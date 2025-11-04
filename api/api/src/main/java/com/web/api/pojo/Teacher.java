package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private Integer id;
    // 姓名
    private String name;
    // 研究方向
    private String title;
    // 邮箱
    private String email;
    // 照片
    private String avatar;
    // 详情
    private String detail;
    // 性别
    private Integer gender;
}
