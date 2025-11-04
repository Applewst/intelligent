create table `team_dynamic`
(
    `id`    tinyint auto_increment comment 'id编号',
    `title` varchar(20) default null comment '标题',
    `desc`  text        default null comment '详情介绍',
    `time`  datetime    default null comment '日期',
    `img`   varchar(50) default null comment '图片路径',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    comment '团队动态表';