package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录实体类
 */
@Data
@AllArgsConstructor
public class LoginVo {
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
}
