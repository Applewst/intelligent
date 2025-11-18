CREATE TABLE `research_direction`
(
    `id`    int NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `image` varchar(128) DEFAULT NULL COMMENT '图片地址',
    `title` varchar(20)  DEFAULT NULL COMMENT '标题',
    `time`  datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='研究方向表'