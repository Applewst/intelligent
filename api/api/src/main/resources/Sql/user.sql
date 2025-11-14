CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(15) NOT NULL,
                        `password` varchar(256) NOT NULL,
                        `salt` varchar(256) DEFAULT NULL,
                        `identity` varchar(20) DEFAULT NULL,
                        `is_disabled` tinyint(1) DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

INSERT INTO `user` (`username`, `password`, `salt`, `identity`, `is_disabled`)
VALUES (
           'default_admin',
           '033092b74e3e91eefee3646b3ce5294252e8ed76aa57b6a04ad43af0977378f9',
           '807890cb0fc032029013a2be02604d11',
           'admin',
           0
       );