package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录实体类
 */
@Data
@AllArgsConstructor
public class LoginVo {
    private String username;    //用户名
    private String password;    //密码
}
