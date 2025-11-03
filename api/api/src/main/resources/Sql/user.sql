CREATE TABLE `user`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `username`    varchar(15)  NOT NULL comment '用户名',
    `password`    varchar(256) NOT NULL comment '加密后的密码',
    `salt`        varchar(256) DEFAULT NULL comment '加密盐',
    `identity`    varchar(20)  DEFAULT NULL comment '权限',
    `is_disabled` tinyint(1)   DEFAULT '0' comment '是否禁用，0未禁用，1已禁用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    comment '用户表';