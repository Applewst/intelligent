package com.web.api.config.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.apache.shiro.ShiroException;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.Serializable;

/**
 * 自定义session管理器
 */
@Component
public class ShiroSessionManager extends DefaultWebSessionManager {

    //从请求中获得sessionId的key
    private static final String AUTHORIZATION = "token";

    //自定义注入的资源类型名称
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    @Autowired
    JwtTokenManager jwtTokenManager;

    protected Serializable getSessionId(ServletRequest request, ServletResponse response) throws ShiroException {
        //判断请求header中是否有jwtToken字段
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            //如果没有携带token，则按照父类的方式在cookie进行获取sessionId(默认实现)
            return super.getSessionId(request, response);
        } else  {
            //如果请求头中有token，则以token的方式获取sessionId
            Claims claims = jwtTokenManager.decodeToken(token);
            //获取id
            String id = claims.getId();

            //标记 sessionId 的来源（这里是自定义的“Stateless request”），方便区分是从 token 还是 cookie 获取的。
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            //把解析出来的 sessionId 存到请求属性，Shiro 后续会用到。
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            //标记这个 sessionId 是有效的，Shiro 会据此判断是否信任该 sessionId。
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            return id;
        }
    }
}
