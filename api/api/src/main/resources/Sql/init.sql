-- sql
CREATE DATABASE IF NOT EXISTS `app`
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
USE `app`;


-- 联系我们表
CREATE TABLE `contact`
(
    `id`             INT NOT NULL AUTO_INCREMENT,
    `email`          VARCHAR(88)  DEFAULT NULL,
    `location`       VARCHAR(40)  DEFAULT NULL,
    `location_image` VARCHAR(255) DEFAULT NULL,
    `wechat_account` VARCHAR(80)  DEFAULT NULL,
    `wechat_qc_code` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='联系我们';

-- 示例数据
INSERT INTO `contact` (`email`, `location`, `location_image`, `wechat_account`, `wechat_qc_code`)
VALUES ('contact@example.com', '上海', 'https://example.com/location1.jpg', 'wechat123',
        'https://example.com/qrcode1.png'),
       ('info@example.com', '北京', 'https://example.com/location2.jpg', 'wechat456',
        'https://example.com/qrcode2.png');

-- 毕业生信息表
CREATE TABLE `graduates`
(
    `id`     INT         NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    `name`   VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT     NOT NULL DEFAULT 0 COMMENT '性别（0=女，1=男）',
    `avatar` VARCHAR(255)         DEFAULT NULL COMMENT '头像URL',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='毕业生信息表';

-- 示例数据
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

-- 论文表
CREATE TABLE `paper`
(
    `id`     int         NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `title`  varchar(35) NOT NULL COMMENT '标题',
    `author` varchar(30) DEFAULT NULL COMMENT '作者',
    `file`   varchar(60) DEFAULT NULL COMMENT '文件URL',
    `time`   date comment '发表时间',
    `detail` text COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='论文';

-- 示例数据
INSERT INTO `paper` (`title`, `author`, `file`, `time`, `detail`)
VALUES ('人工智能综述', '王志鹏', 'C:/papers/AI_review.pdf', '2025-03-12', '关于人工智能的发展综述与展望'),
       ('机器学习应用', '李明轩', 'C:/papers/ML_app.pdf', '2025-06-01', '机器学习在实际工程中的应用案例'),
       ('深度学习优化', '祁婷婷', 'C:/papers/DL_optimization.pdf', '2024-11-20', '模型训练与推理性能优化方法研究'),
       ('自然语言处理进展', '林思雨', 'C:/papers/NLP_progress.pdf', '2025-01-15', '语言理解与生成最新进展综述'),
       ('计算机视觉新技术', '陈俊豪', 'C:/papers/CV_newtech.pdf', '2025-07-30', '目标检测与分割的改进方法');

-- 照片墙
CREATE TABLE `picture_wall`
(
    `id`     int NOT NULL AUTO_INCREMENT,
    `title`  varchar(30)  DEFAULT NULL COMMENT '获得主题',
    `file`   varchar(255) DEFAULT NULL COMMENT 'URL',
    `time`   date         DEFAULT NULL COMMENT '时间',
    `detail` text COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='照片墙';

-- 示例数据
INSERT INTO `picture_wall` (`title`, `file`, `time`, `detail`)
VALUES ('迎新晚会', 'https://example.com/pic1.jpg', '2025-09-01', '新生欢迎晚会精彩瞬间'),
       ('毕业典礼', 'https://example.com/pic2.jpg', '2025-06-15', '毕业生合影与颁奖仪式'),
       ('实验室年度合影', 'https://example.com/pic3.jpg', '2025-11-10', '科研团队与师生合影'),
       ('学术沙龙', 'https://example.com/pic4.jpg', '2025-10-05', '学术沙龙交流与报告'),
       ('社会实践', 'https://example.com/pic5.jpg', '2025-07-20', '学生暑期社会实践活动'),
       ('编程马拉松', 'https://example.com/pic6.jpg', '2025-08-12', '24小时编程挑战赛精彩剪影');

-- 研究方向
CREATE TABLE `research_direction`
(
    `id`    INT NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `image` VARCHAR(128) DEFAULT NULL COMMENT '图片地址',
    `title` VARCHAR(20)  DEFAULT NULL COMMENT '标题',
    `time`  DATETIME     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='研究方向表';

-- 示例数据
INSERT INTO `research_direction` (`image`, `title`, `time`)
VALUES ('https://example.com/research1.jpg', '深度学习', '2025-11-01 10:00:00'),
       ('https://example.com/research2.jpg', '自然语言处理', '2025-11-05 14:30:00');

-- 资源文件信息表
CREATE TABLE resource
(
    id          VARCHAR(64) PRIMARY KEY COMMENT '资源文件ID',
    name        VARCHAR(255) NOT NULL COMMENT '文件名称',
    size        VARCHAR(50) COMMENT '文件大小（例如：12MB）',
    type        VARCHAR(50) COMMENT '文件类型（如：pdf、jpg、mp4等）',
    url         VARCHAR(500) COMMENT '文件存储路径',
    update_time DATETIME COMMENT '更新时间',
    downloads   INT DEFAULT 0 COMMENT '下载次数'
) COMMENT ='资源文件信息表';

-- 示例数据
INSERT INTO resource (id, name, size, type, url, update_time, downloads)
VALUES ('f1a2b3c4d5e6', '用户手册.pdf', '2.3MB', 'pdf', 'C:/uploads/20251111/用户手册.pdf', '2025-11-01 10:23:45', 15),
       ('a9b8c7d6e5f4', '产品宣传图.jpg', '1.2MB', 'jpg', 'C:/uploads/20251111/产品宣传图.jpg', '2025-10-29 08:12:33',
        42),
       ('c1d2e3f4a5b6', '版本更新说明.docx', '856KB', 'docx', 'C:/uploads/20251111/版本更新说明.docx',
        '2025-10-30 15:45:10', 27),
       ('d2e3f4a5b6c7', '安装包.zip', '35MB', 'zip', 'C:/uploads/20251111/安装包.zip', '2025-11-03 11:05:22', 63),
       ('e3f4a5b6c7d8', '产品演示视频.mp4', '128MB', 'mp4', 'C:/uploads/20251111/产品演示视频.mp4',
        '2025-11-05 19:32:58', 105),
       ('f4a5b6c7d8e9', '配置示例.json', '12KB', 'json', 'C:/uploads/20251111/配置示例.json', '2025-11-02 09:15:47', 8),
       ('a5b6c7d8e9f0', '接口文档.yaml', '24KB', 'yaml', 'C:/uploads/20251111/接口文档.yaml', '2025-11-04 14:26:10',
        19),
       ('b6c7d8e9f0a1', '测试数据.csv', '540KB', 'csv', 'C:/uploads/20251111/测试数据.csv', '2025-11-06 17:54:33', 31),
       ('c7d8e9f0a1b2', '系统架构图.png', '980KB', 'png', 'C:/uploads/20251111/系统架构图.png', '2025-11-07 13:42:19',
        76),
       ('V4dpnYuR', '圆头耄耋', '402KB', 'png', '/resources / 20251118 / fb2af1acf3f145a19317f2c636763156.png',
        '2025 - 11 - 19 01:26:56', 4);


-- 科研团队动态
CREATE TABLE `scientific_dynamic`
(
    `id`     INT NOT NULL AUTO_INCREMENT,
    `image`  VARCHAR(50) DEFAULT NULL COMMENT '图片地址',
    `title`  VARCHAR(20) DEFAULT NULL COMMENT '标题',
    `detail` TEXT COMMENT '内容',
    `time`   DATETIME    DEFAULT NULL COMMENT '时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='科研团队动态表';

-- 示例数据
INSERT INTO `scientific_dynamic` (`image`, `title`, `detail`, `time`)
VALUES ('https://example.com/dynamic1.jpg', '团队获奖', '科研团队在AI竞赛中获奖', '2025-10-20 09:00:00'),
       ('https://example.com/dynamic2.jpg', '项目启动', '新研究项目正式启动', '2025-11-01 14:00:00');

-- 在读学生信息表
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

-- 示例数据
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


-- 学生发展
CREATE TABLE `student_development`
(
    `id`     INT NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(20)  DEFAULT NULL COMMENT '姓名',
    `file`   VARCHAR(255) DEFAULT NULL COMMENT '文件URL',
    `time`   DATE         DEFAULT NULL COMMENT '获奖时间',
    `detail` TEXT COMMENT '详情',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='学生发展';

-- 示例数据
INSERT INTO `student_development` (`name`, `file`, `time`, `detail`)
VALUES ('勤阳', 'https://example.com/dev1.pdf', '2025-06-01', '获得数学建模竞赛一等奖'),
       ('洋熙成', 'https://example.com/dev2.pdf', '2025-07-15', '参加全国创新创业大赛');

-- 学生获奖
CREATE TABLE `student_prize`
(
    `id`     int         NOT NULL AUTO_INCREMENT,
    `author` varchar(20) NOT NULL COMMENT '获奖人',
    `file`   varchar(255) DEFAULT NULL COMMENT '文件URL',
    `time`   date         DEFAULT NULL COMMENT '获奖时间',
    `total`  int          DEFAULT NULL COMMENT '总数',
    `detail` text COMMENT '详情',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='学生获奖';

-- 示例数据
INSERT INTO `student_prize` (`author`, `file`, `time`, `total`, `detail`)
VALUES ('张天翼', 'https://example.com/prize1.pdf', '2025-05-20', 1, '获得编程竞赛二等奖'),
       ('赵雅彤', 'https://example.com/prize2.pdf', '2025-08-10', 2, '获得数学竞赛一等奖');

-- 教师信息表
CREATE TABLE teacher
(
    id     INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name   VARCHAR(50) NOT NULL COMMENT '姓名',
    title  VARCHAR(100) COMMENT '研究方向',
    email  VARCHAR(100) UNIQUE COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像URL',
    detail TEXT COMMENT '详情介绍',
    gender TINYINT(1) COMMENT '性别，1=男，0=女'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='教师信息表';

-- 示例数据
INSERT INTO teacher (name, title, email, avatar, detail, gender)
VALUES ('祁婷婷', '神经网络', 'ob5j6i_gl4ivm@qq.com', 'https://avatars.githubusercontent.com/u/95110235',
        '主要研究人工神经网络与深度学习模型优化。', 1),
       ('林思雨', '自然语言处理', 'lisy_nlp@qq.com', 'https://avatars.githubusercontent.com/u/38120314',
        '研究方向包括语义理解与生成模型。', 0),
       ('王志鹏', '机器学习', 'wzp_ml@qq.com', 'https://avatars.githubusercontent.com/u/20549133',
        '关注算法可解释性与模型压缩技术。', 1),
       ('赵婧怡', '数据挖掘', 'zjy_dm@qq.com', 'https://avatars.githubusercontent.com/u/57291445',
        '从事大数据分析与特征工程研究。', 0),
       ('陈俊豪', '计算机视觉', 'cjh_cv@qq.com', 'https://avatars.githubusercontent.com/u/82345119',
        '主要研究目标检测与图像识别算法。', 1),
       ('黄依婷', '智能推荐系统', 'hyt_rs@qq.com', 'https://avatars.githubusercontent.com/u/99203122',
        '研究兴趣包括用户画像与推荐策略。', 0),
       ('李建峰', '嵌入式系统', 'ljf_emb@qq.com', 'https://avatars.githubusercontent.com/u/63491200',
        '专注于边缘计算与智能硬件集成。', 1),
       ('周雪莹', '人机交互', 'zxy_hci@qq.com', 'https://avatars.githubusercontent.com/u/40291288',
        '研究方向为交互式设计与用户体验优化。', 0),
       ('郭思源', '强化学习', 'gsy_rl@qq.com', 'https://avatars.githubusercontent.com/u/70319201',
        '研究多智能体系统与策略优化算法。', 1),
       ('徐若涵', '智能语音', 'xrh_speech@qq.com', 'https://avatars.githubusercontent.com/u/91450333',
        '从事语音识别与情感计算研究。', 0);


-- 团队介绍
CREATE TABLE `team`
(
    `id`           INT         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `team_name`    VARCHAR(20) NOT NULL COMMENT '团队名称',
    `introduction` TEXT COMMENT '团队介绍',
    `image_url`    VARCHAR(50) DEFAULT NULL COMMENT '图片',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT ='团队介绍';

-- 示例数据
INSERT INTO `team` (`team_name`, `introduction`, `image_url`)
VALUES ('AI创新团队', '致力于人工智能应用开发', 'https://example.com/team1.jpg');

-- 团队动态
create table `team_dynamic`
(
    `id`     int NOT NULL AUTO_INCREMENT,
    `image`  varchar(50) DEFAULT NULL comment '图片地址',
    `title`  varchar(20) DEFAULT NULL comment '标题',
    `detail` text comment '内容',
    `time`   datetime    DEFAULT NULL comment '时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    comment '团队动态表';

-- 示例数据
INSERT INTO `team_dynamic` (`image`, `title`, `detail`, `time`)
VALUES ('https://example.com/team_dynamic1.jpg', '团队培训', '团队进行了技能培训与实战演练，提升了成员协作能力。',
        '2025-09-10 10:00:00'),
       ('https://example.com/team_dynamic2.jpg', '项目展示', '展示了最新项目成果，获得校内外广泛关注与好评。',
        '2025-10-05 14:30:00'),
       ('https://example.com/team_dynamic3.jpg', '成员获奖', '团队成员李华在全国竞赛中获得一等奖。',
        '2025-11-12 16:20:00'),
       ('https://example.com/team_dynamic4.jpg', '外部合作', '与某高校签署联合实验室合作协议，共建研发平台。',
        '2025-08-21 09:15:00'),
       ('https://example.com/team_dynamic5.jpg', '学术研讨', '举办学术研讨会，邀请多位领域专家开展主题报告与讨论。',
        '2025-07-03 13:45:00'),
       ('https://example.com/team_dynamic6.jpg', '新品发布', '团队发布新版本工具包并开放源码，便于社区使用与贡献。',
        '2025-11-15 11:00:00');

-- 用户表
CREATE TABLE `user`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `username`    varchar(15)  NOT NULL,
    `password`    varchar(256) NOT NULL,
    `salt`        varchar(256) DEFAULT NULL,
    `identity`    varchar(20)  DEFAULT NULL,
    `is_disabled` tinyint(1)   DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

-- 示例数据
INSERT INTO `user` (`username`, `password`, `salt`, `identity`, `is_disabled`)
VALUES ('default_admin',
        '033092b74e3e91eefee3646b3ce5294252e8ed76aa57b6a04ad43af0977378f9',
        '807890cb0fc032029013a2be02604d11',
        'admin',
        0);
