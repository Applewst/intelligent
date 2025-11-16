package com.web.api.pojo;

import lombok.Data;

/**
 * JWT数据实体类
 * @author Askr-Yggdrasill
 */
@Data
public class JwtData {
    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private String identity;
}
