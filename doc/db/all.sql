# 电子书表
drop table if exists `ebook`;
create table `ebook` (
  `id` bigint not null comment 'id',
  `name` varchar(50) comment '名称',
  `category1_id` bigint comment '分类1',
  `category2_id` bigint comment '分类2',
  `description` varchar(200) comment '描述',
  `cover` varchar(200) comment '封面',
  `doc_count` int comment '文档数',
  `view_count` int comment '阅读数',
  `vote_count` int comment '点赞数',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='电子书';

insert into `ebook` (id, name, description) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');

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

# 分类
drop table if exists `category`;
create table `category` (
                            `id` bigint not null comment 'id',
                            `parent` bigint not null default 0 comment 'parent id',
                            `name` varchar(50) not null comment 'name',
                            `sort` int comment 'order',
                            primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='category';

insert into `category` (id, parent, name, sort) values (100, 000, 'cat 1', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'cat 1-1', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'cat 1-2', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'cat 2', 200);
insert into `category` (id, parent, name, sort) values (201, 200, 'cat 2-1', 201);
insert into `category` (id, parent, name, sort) values (202, 200, 'cat 2-2', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'cat 3', 300);
insert into `category` (id, parent, name, sort) values (301, 300, 'cat 3-1', 301);
insert into `category` (id, parent, name, sort) values (302, 300, 'cat 3-2', 302);
insert into `category` (id, parent, name, sort) values (400, 000, 'cat 4', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'cat 4-1', 401);
insert into `category` (id, parent, name, sort) values (500, 000, 'cat 5', 500);
insert into `category` (id, parent, name, sort) values (501, 500, 'cat 5-1', 501);
insert into `category` (id, parent, name, sort) values (502, 500, 'cat 5-2', 502);
insert into `category` (id, parent, name, sort) values (503, 500, 'cat 5-3', 503);

-- 文档表
drop table if exists `doc`;
create table `doc` (
                       `id` bigint not null comment 'id',
                       `ebook_id` bigint not null default 0 comment 'ebook id',
                       `parent` bigint not null default 0 comment 'parent',
                       `name` varchar(50) not null comment 'name',
                       `sort` int comment 'sort',
                       `view_count` int default 0 comment 'view count',
                       `vote_count` int default 0 comment 'vote count',
                       primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='doc';

insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (1, 1, 0, 'doc1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (2, 1, 1, 'doc1.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (3, 1, 0, 'doc2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (4, 1, 3, 'doc2.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (5, 1, 3, 'doc2.2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (6, 1, 5, 'doc2.2.1', 1, 0, 0);


drop table if exists `content`;
create table `content` (
                           `id` bigint not null comment 'content_id',
                           `content` mediumtext not null comment 'content',
                           primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='content';
