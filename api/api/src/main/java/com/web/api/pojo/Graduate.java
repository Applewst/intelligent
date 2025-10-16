package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 毕业生类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Graduate {
    private int id;
    /** 姓名 */
    private String name;
    /** 年级 */
    private String grade;
    /** 专业 */
    private String major;
    /** 头像路径 */
    private String avatar;
}
