INSERT INTO public.db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag)
VALUES(true, 1, 1, true, now(), 'node1');

INSERT INTO public.db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag)
VALUES(true, 2, 2, false, now(), 'node2');

INSERT INTO public.db_node
(alive_or_not, electe_order, id, master_or_not, refresh_time, node_tag)
VALUES(true, 3, 3, false, now(), 'node3');