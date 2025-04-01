create table db_node(
  id integer not null,
  electe_order integer not null unique,
  alive_or_not boolean not null,
  master_or_not boolean not null,
  refresh_time timestamp(6) with time zone not null,
  node_tag varchar(255) not null unique,
  update_time timestamp(6) with time zone not null,
  update_node_tag varchar(255) not null,
  primary key (id))