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
    private Integer id;
    // 姓名
    private String name;
    //  性别 0-女 1-男
    private Integer gender;
    // 头像URL
    private String avatar;
}
