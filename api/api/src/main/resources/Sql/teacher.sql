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
