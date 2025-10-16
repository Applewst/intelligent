package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 在读学生类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    /** 姓名 */
    private String name;
    /** 年级 */
    private String grade;
    /** 专业 */
    private String major;
    /** 头像路径 */
    private String avatar;
}
