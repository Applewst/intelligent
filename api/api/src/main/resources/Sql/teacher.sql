create table `teacher`(
`id` tinyint not null auto_increment,
`name` varchar(5) default null comment '姓名',
`title` varchar(20) default null comment '文章',
`email` varchar(50) default null comment '邮箱',
`avatar` varchar(50) default null comment '头像地址',
`info` text default null comment '详情',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
comment '教师信息表';