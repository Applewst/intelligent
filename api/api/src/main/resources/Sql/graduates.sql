CREATE TABLE `graduates`
(
    `id`     INT         NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    `name`   VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT     NOT NULL DEFAULT 0 COMMENT '性别（0=女，1=男）',
    `avatar` VARCHAR(255)         DEFAULT NULL COMMENT '头像URL',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='毕业生信息表';


INSERT INTO `graduates` (`id`, `name`, `gender`, `avatar`)
VALUES (1, '洋熙成', 1, 'https://avatars.githubusercontent.com/u/31575119'),
       (2, '李明轩', 1, 'https://avatars.githubusercontent.com/u/10293847'),
       (3, '王子涵', 0, 'https://avatars.githubusercontent.com/u/20485732'),
       (4, '陈思雨', 0, 'https://avatars.githubusercontent.com/u/39847561'),
       (5, '赵宇航', 1, 'https://avatars.githubusercontent.com/u/51234789'),
       (6, '孙若琳', 0, 'https://avatars.githubusercontent.com/u/63459872'),
       (7, '周子杰', 1, 'https://avatars.githubusercontent.com/u/74592310'),
       (8, '刘心怡', 0, 'https://avatars.githubusercontent.com/u/85924357'),
       (9, '黄梓豪', 1, 'https://avatars.githubusercontent.com/u/96321458'),
       (10, '张雪晴', 0, 'https://avatars.githubusercontent.com/u/107894512');
