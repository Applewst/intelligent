CREATE TABLE `picture_wall`
(
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(30) DEFAULT NULL COMMENT '获得主题',
    `file` varchar(255) DEFAULT NULL COMMENT 'URL',
    `time` date DEFAULT NULL COMMENT '时间',
    `detail` text COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='照片墙'