drop table if exists `test`;
create table `test` (
  `id` bigint not null comment 'id',
  `name` varchar(50) comment 'name',
  `password` varchar(50) comment 'password',
  primary key (`id`)
) engine=innodb default charset=utf8 comment='test';

drop table if exists `demo`;
create table `demo` (
  `id` bigint not null comment 'id',
  `name` varchar(50) comment 'name',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='for test';

insert into `demo` (id, name) values (1, 'test');

