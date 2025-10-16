create table `student`(
`id`  tinyint not null auto_increment,
`name` varchar(5) default null comment '姓名',
`grade` varchar(8) default null comment '年级',
`major` varchar(20) default null comment '专业',
`avatar` varchar(50) default null comment '头像地址',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
comment '在读学生信息表';