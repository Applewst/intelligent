package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在读学生类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
    // 姓名
    private String name;
    // 年级
    private String grade;
    // 学工号
    private String stucode;
    // 性别 女-0 男-1
    private Integer gender;
    // 头像URL
    private String avatar;
}
