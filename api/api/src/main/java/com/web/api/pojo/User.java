package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 盐 */
    private String salt;
    /** 权限 */
    private String identity;
    /** 是否禁用 */
    private boolean isDisabled; // true表示禁用，false表示未禁用
}
