package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Table
@Entity
public class DbNode {
  @Id
  private Integer id;
  @Column(nullable = false,unique = true)
  private Integer electeOrder;
  @Column(nullable = false)
  private Boolean aliveOrNot;
  @Column(nullable = false)
  private Boolean masterOrNot;
  @Column(nullable = false)
  private Instant refreshTime;
  @Column(nullable = false,unique = true)
  private String nodeTag;
  @Column(nullable = false)
  private Instant updateTime;
  @Column(nullable = false)
  private String updateNodeTag;
  @Column(nullable = false)
  private String groupId;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ClusterState clusterState;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getElecteOrder() {
    return electeOrder;
  }

  public void setElecteOrder(Integer electeOrder) {
    this.electeOrder = electeOrder;
  }

  public Boolean getMasterOrNot() {
    return masterOrNot;
  }

  public void setMasterOrNot(Boolean masterOrNot) {
    this.masterOrNot = masterOrNot;
  }

  public Instant getRefreshTime() {
    return refreshTime;
  }

  public void setRefreshTime(Instant refreshTime) {
    this.refreshTime = refreshTime;
  }

  public String getNodeTag() {
    return nodeTag;
  }

  public void setNodeTag(String nodeTag) {
    this.nodeTag = nodeTag;
  }

  public Boolean getAliveOrNot() {
    return aliveOrNot;
  }

  public void setAliveOrNot(Boolean aliveOrNot) {
    this.aliveOrNot = aliveOrNot;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateNodeTag() {
    return updateNodeTag;
  }

  public void setUpdateNodeTag(String updateNodeTag) {
    this.updateNodeTag = updateNodeTag;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public ClusterState getClusterState() {
    return clusterState;
  }

  public void setClusterState(ClusterState clusterState) {
    this.clusterState = clusterState;
  }
}
