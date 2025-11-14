CREATE TABLE student
(
    id      INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name    VARCHAR(50)        NOT NULL COMMENT '学生姓名',
    grade   VARCHAR(20) COMMENT '年级',
    stucode VARCHAR(20) UNIQUE NOT NULL COMMENT '学号',
    gender  TINYINT(1) COMMENT '性别，1表示男，0表示女',
    avatar  VARCHAR(255) COMMENT '头像URL'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='在读学生信息表';


INSERT INTO student (name, grade, stucode, gender, avatar)
VALUES ('勤阳', '大三', '240809030111', 1, 'https://avatars.githubusercontent.com/u/22797302'),
       ('洋熙成', '大二', '240809030112', 1, 'https://avatars.githubusercontent.com/u/31575119'),
       ('林婉清', '大一', '240809030113', 0, 'https://avatars.githubusercontent.com/u/63125211'),
       ('张天翼', '大三', '240809030114', 1, 'https://avatars.githubusercontent.com/u/21590122'),
       ('赵雅彤', '大四', '240809030115', 0, 'https://avatars.githubusercontent.com/u/48793117'),
       ('李晨曦', '大二', '240809030116', 1, 'https://avatars.githubusercontent.com/u/51234512'),
       ('陈沐雪', '大一', '240809030117', 0, 'https://avatars.githubusercontent.com/u/27850109'),
       ('黄景轩', '大三', '240809030118', 1, 'https://avatars.githubusercontent.com/u/39102920'),
       ('周语彤', '大二', '240809030119', 0, 'https://avatars.githubusercontent.com/u/48322156'),
       ('郭子豪', '大四', '240809030120', 1, 'https://avatars.githubusercontent.com/u/21999234');
