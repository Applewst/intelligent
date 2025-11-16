CREATE TABLE `student_development`
(
    `id`     int NOT NULL AUTO_INCREMENT,
    `name`   varchar(20)  DEFAULT NULL COMMENT '姓名',
    `file`   varchar(255) DEFAULT NULL COMMENT '文件URL',
    `time`   date         DEFAULT NULL COMMENT '获奖时间',
    `detail` text COMMENT '详情',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='学生发展'