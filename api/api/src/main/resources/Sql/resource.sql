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
       ('d8e9f0a1b2c3', '使用指南.txt', '48KB', 'txt', 'C:/uploads/20251111/使用指南.txt', '2025-11-08 20:11:08', 22);

