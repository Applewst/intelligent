package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;   //加密盐
    private String identity;    //权限身份
    private boolean isDisabled; //是否禁用
}
