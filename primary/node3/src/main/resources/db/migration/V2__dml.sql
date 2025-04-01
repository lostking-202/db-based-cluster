INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 1, 1, true, now(), 'node1',now(),'node1','A','DEAD');

INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 2, 2, false, now(), 'node2',now(),'node2','A','DEAD');

INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 3, 3, false, now(), 'node3',now(),'node3','A','DEAD');

INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 4, 4, true, now(), 'node4',now(),'node4','B','WORK');

INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 5, 5, true, now(), 'node5',now(),'node5','B','WORK');

INSERT INTO db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag,update_time,update_node_tag,group_id,cluster_state)
VALUES(true, 6, 6, true, now(), 'node6',now(),'node6','B','WORK');