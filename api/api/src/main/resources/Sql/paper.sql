CREATE TABLE `paper`
(
    `id`     int         NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `title`  varchar(35) NOT NULL COMMENT '标题',
    `author` varchar(30) DEFAULT NULL COMMENT '作者',
    `file`   varchar(255) DEFAULT NULL COMMENT '文件URL',
    `time`   date comment '发表时间',
    `detail` text COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='论文'