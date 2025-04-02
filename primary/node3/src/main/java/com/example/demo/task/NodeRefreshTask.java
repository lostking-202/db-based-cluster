package com.example.demo.task;

import com.example.demo.entity.ClusterState;
import com.example.demo.entity.DbNode;
import com.example.demo.mapper.DbNodeMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeRefreshTask implements InitializingBean {

  @Autowired
  DbNodeMapper dbNodeMapper;
  @Value("${nodeTag}")
  String nodeTag;
  @Value("${refreshDurateMills}")
  Long refreshDurateMills;

  @Override
  public void afterPropertiesSet() throws Exception {
    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
      @Override
      public void run() {
        try {

          var currentNode=dbNodeMapper.getDbNodeByTag(nodeTag);
          var nodeTag=currentNode.getNodeTag();
          var groupId=currentNode.getGroupId();
          var now=Instant.now();

          if(currentNode.getClusterState()==ClusterState.DEAD){
            currentNode.setUpdateTime(now);
            currentNode.setUpdateNodeTag(nodeTag);
            currentNode.setClusterState(ClusterState.STANDBY);
            dbNodeMapper.updateClusterState(currentNode);
          }

          currentNode.setRefreshTime(now);
          currentNode.setUpdateTime(now);
          currentNode.setUpdateNodeTag(nodeTag);
          dbNodeMapper.updateRefreshTime(currentNode);
          if(!currentNode.getAliveOrNot()){
            currentNode.setAliveOrNot(true);
            currentNode.setUpdateTime(now);
            currentNode.setUpdateNodeTag(nodeTag);
            dbNodeMapper.updateAliveState(currentNode);
          }

          // 判断主节点假活
          var masterNode=dbNodeMapper.getMasterNode(groupId);
          if(masterNode!=null){
            if(Duration.between(masterNode.getRefreshTime(),now).toMillis()>refreshDurateMills*2){
              masterNode.setMasterOrNot(false);
              masterNode.setUpdateTime(now);
              masterNode.setUpdateNodeTag(nodeTag);
              dbNodeMapper.updateMasterState(masterNode);
            }
          }

          // 获取所有活着的节点
          var aliveNodes=dbNodeMapper.getAllAliveDbNodes(groupId);
          var realAliveNodes=aliveNodes.stream().filter(node->{
            if(Duration.between(node.getRefreshTime(),now).toMillis()>refreshDurateMills*2){
              return false;
            }
            return true;
          }).collect(Collectors.toList());
          // 先判断是否有主节点,如果没有主节点
          if(realAliveNodes.stream().filter(DbNode::getMasterOrNot).findAny().isEmpty()){
            // 如果自己不是最大的就不管

            if(nodeTag.equals(realAliveNodes.stream().max(Comparator.comparingInt(DbNode::getElecteOrder)).get().getNodeTag())){
              currentNode.setMasterOrNot(true);
              currentNode.setUpdateTime(now);
              currentNode.setUpdateNodeTag(nodeTag);
              dbNodeMapper.updateMasterState(currentNode);
            }
          }

          if(currentNode.getMasterOrNot()){
            // 第一，干掉标记存活却死掉的节点
            aliveNodes.stream().forEach(node->{
              if(Duration.between(node.getRefreshTime(),now).toMillis()>refreshDurateMills*2){
                node.setAliveOrNot(false);
                node.setUpdateTime(now);
                node.setUpdateNodeTag(nodeTag);
                dbNodeMapper.updateAliveState(node);
              }
            });
            // 第二，死掉的节点如果有标记为主节点的，修改为非主节点
            var deadNodes=dbNodeMapper.getAllDeadDbNodes(groupId);
            deadNodes.stream().filter(DbNode::getMasterOrNot).forEach(node->{
              node.setMasterOrNot(false);
              node.setUpdateTime(now);
              node.setClusterState(ClusterState.DEAD);
              node.setUpdateNodeTag(nodeTag);
              dbNodeMapper.updateMasterState(node);
            });
          }

          // 另一个集群挂了需要做的事
          var primaryNodes=dbNodeMapper.getOtherGroupDbNodes(groupId);

          if(!primaryNodes.isEmpty()&&primaryNodes.stream().filter(node->node.getClusterState()==ClusterState.WORK).findAny().isEmpty()){
            return;
          }

          // 查看是否工作集群所有节点都挂了
          if(primaryNodes.stream().filter(node-> Duration.between(node.getRefreshTime(),now).toMillis()<=refreshDurateMills*2).findAny().isEmpty()){
            if(currentNode.getMasterOrNot()){
              primaryNodes.stream().forEach(node->{
                node.setClusterState(ClusterState.DEAD);
                node.setUpdateTime(now);
                node.setUpdateNodeTag(nodeTag);
                dbNodeMapper.updateClusterState(node);
              });
              // 切换一次就够了
              realAliveNodes.stream().filter(node->node.getClusterState()!=ClusterState.WORK).forEach(node->{
                node.setClusterState(ClusterState.WORK);
                node.setUpdateTime(now);
                node.setUpdateNodeTag(nodeTag);
                dbNodeMapper.updateClusterState(node);
              });
            }
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    };
    timer.schedule(timerTask,refreshDurateMills,refreshDurateMills);
  }
}
