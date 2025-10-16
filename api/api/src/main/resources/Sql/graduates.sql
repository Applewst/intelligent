create table `graduates`(
`id` int not null auto_increment,
`name` varchar(5) DEFAULT NULL comment '姓名',
`grade` varchar(8) DEFAULT NULL comment '年级',
`major` varchar(20) DEFAULT NULL comment '专业',
`avatar` varchar(50) DEFAULT NULL comment '头像地址',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
comment '毕业生信息表';