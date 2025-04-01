CREATE TABLE public.db_node (
	alive_or_not bool NOT NULL,
	electe_order int4 NOT NULL,
	id int4 NOT NULL,
	master_or_not bool NOT NULL,
	refresh_time timestamptz(6) NOT NULL,
	update_time timestamptz(6) NOT NULL,
	cluster_state varchar(255) NOT NULL,
	group_id varchar(255) NOT NULL,
	node_tag varchar(255) NOT NULL,
	update_node_tag varchar(255) NOT NULL,
	CONSTRAINT db_node_cluster_state_check CHECK (((cluster_state)::text = ANY ((ARRAY['STANDBY'::character varying, 'WORK'::character varying, 'DEAD'::character varying])::text[]))),
	CONSTRAINT db_node_electe_order_key UNIQUE (electe_order),
	CONSTRAINT db_node_node_tag_key UNIQUE (node_tag),
	CONSTRAINT db_node_pkey PRIMARY KEY (id)
);