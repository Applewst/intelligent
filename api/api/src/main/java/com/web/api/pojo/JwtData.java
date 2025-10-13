package com.web.api.pojo;

import lombok.Data;

/**
 * JWT数据实体类
 */
@Data
public class JwtData {
    /**
     * 用户id
     */
    private String Id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private int role; //0:普通用户 1:管理员
}
