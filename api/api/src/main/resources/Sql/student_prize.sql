CREATE TABLE `student_prize` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `author` varchar(20) NOT NULL COMMENT '获奖人',
                                 `file` varchar(255) DEFAULT NULL COMMENT '文件URL',
                                 `time` date DEFAULT NULL COMMENT '获奖时间',
                                 `total` int DEFAULT NULL COMMENT '总数',
                                 `detail` text COMMENT '详情',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生获奖'