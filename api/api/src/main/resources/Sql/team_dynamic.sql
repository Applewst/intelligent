create table `team_dynamic`
(
    `id`     int NOT NULL AUTO_INCREMENT,
    `image`  varchar(255) DEFAULT NULL comment '图片地址',
    `title`  varchar(20) DEFAULT NULL comment '标题',
    `detail` text comment '内容',
    `time`   datetime    DEFAULT NULL comment '时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    comment '团队动态表';