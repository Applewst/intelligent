create table `research_direction`
(
    `id`       int auto_increment not null comment 'id编号',
    `imageUrl` varchar(50) DEFAULT NULL comment '图片地址',
    `title`    varchar(20) DEFAULT NULL comment '标题',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    comment '研究方向表';