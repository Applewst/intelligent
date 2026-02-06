-- auto-generated definition
create table research_direction_group
(
    id     int auto_increment
        primary key,
    name   varchar(255) null,
    detail text         null,
    author varchar(255) null,
    file   varchar(255) null,
    time   time         null,
    total  text         null
)ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='研究方向课题组表'

