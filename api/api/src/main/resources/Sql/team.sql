CREATE TABLE `team`
(
    `id`           int         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `team_name`    varchar(20) NOT NULL COMMENT '团队名称',
    `introduction` text COMMENT '团队介绍',
    `image_url`    varchar(50) DEFAULT NULL COMMENT '图片',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='团队介绍'