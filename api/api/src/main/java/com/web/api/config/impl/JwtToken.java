package com.web.api.config.impl;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken 用于封装 JWT 令牌信息
 * @author Askr-Yggdrasill
 */
public class JwtToken implements AuthenticationToken {
    private final String username;

    public JwtToken(String username) {
        this.username = username;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    // JWT 不需要密码
    @Override
    public Object getCredentials() {
        return "";
    }
}