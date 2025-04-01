package com.example.demo.mapper;

import com.example.demo.entity.DbNode;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbNodeMapper {
  // 更新自己的节点的刷新时间
  Boolean updateRefreshTime(DbNode node);
  // 获取所有活着的节点
  List<DbNode> getAllAliveDbNodes(String groupId);
  // 获取自己节点的信息
  DbNode getDbNodeByTag(String nodeTag);
  // 修改节点的主从状态
  Boolean updateMasterState(DbNode node);
  // 修改节点的存活状态
  Boolean updateAliveState(DbNode node);

  List<DbNode> getAllDeadDbNodes(String groupId);

  DbNode getMasterNode(String groupId);

  List<DbNode> getOtherGroupDbNodes(String groupId);

  Boolean updateClusterState(DbNode currentNode);
}
