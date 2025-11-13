CREATE TABLE `contact` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `email` varchar(88) DEFAULT NULL,
                           `location` varchar(40) DEFAULT NULL,
                           `location_image` varchar(255) DEFAULT NULL,
                           `wechat_account` varchar(80) DEFAULT NULL,
                           `wechat_qc_code` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='联系我们'