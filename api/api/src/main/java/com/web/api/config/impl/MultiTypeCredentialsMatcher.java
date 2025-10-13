package com.web.api.config.impl;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 多类型凭证匹配器
 */
public class MultiTypeCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 如果是 JwtToken，直接返回 true
        if (token instanceof JwtToken) {
            return true;
        }
        // 否则，使用父类的方法进行密码匹配
        return super.doCredentialsMatch(token, info);
    }
}
