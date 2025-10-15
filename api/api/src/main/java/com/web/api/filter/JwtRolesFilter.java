package com.web.api.filter;

import com.alibaba.fastjson2.JSONObject;
import com.web.api.config.impl.JwtToken;
import com.web.api.config.impl.JwtTokenManager;
import com.web.api.error.Error;
import com.web.api.exception.AccountBaned;
import com.web.api.pojo.JwtData;
import com.web.api.pojo.Result;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import java.io.IOException;
import java.util.Set;

/**
 * 自定义权限过滤器: 重写原有过滤器 roles(必须拥有某个角色权限才能访问)
 */
@Slf4j
public class JwtRolesFilter extends RolesAuthorizationFilter {

    private final JwtTokenManager jwtTokenManager;

    public JwtRolesFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    /**
     * 访问拒绝时执行的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String reason = (String) httpRequest.getAttribute("authFailReason");

        String msg;
        if ("invalid_token".equals(reason)) {
            msg = com.web.api.error.Error.NOACCOUNT.toString();
        } else if ("account_baned".equals(reason)) {
            msg = com.web.api.error.Error.ACCOUNTBAN.toString();
        } else if ("missing_token".equals(reason)) {
            msg = Error.NoToken.toString();
        } else {
            msg = "认证失败";
        }
        //如果没有，返回响应
        Result result = Result.error(msg);
        writeJsonResponse(response, result, HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        //判断当前请求头中是否带有jwtToken的字符串
        String jwtToken = WebUtils.toHttp(request).getHeader("token");

        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        // 1. 没有 token
        if (jwtToken == null || jwtToken.isEmpty()) {
            // 设置拒绝原因，便于 onAccessDenied 使用
            httpRequest.setAttribute("authFailReason", "missing_token");
            return false;
        }

        // 2. 有 token，验证
        try {
            if (jwtTokenManager.isVerifyToken(jwtToken)) {
                //登录
                Subject subject = getSubject(request, response);
                //解析用户名
                Claims claims = jwtTokenManager.decodeToken(jwtToken);
                String userJson = (String) claims.get("user");
                JwtData data = JSONObject.parseObject(userJson, JwtData.class);
                String username = data.getUsername();
                if (subject.getPrincipal() == null) {
                    //这里用自定义的 JwtToken
                    AuthenticationToken token = new JwtToken(username);
                    subject.login(token);
                }
                //获取身份表
                String[] rolesArray = (String[]) mappedValue;
                //判断是否拥有对应角色
                if (rolesArray != null && rolesArray.length != 0) {
                    Set<String> roles = CollectionUtils.asSet(rolesArray);
                    return subject.hasAllRoles(roles);
                } else {
                    //无权限要求时，放行
                    return true;
                }
            } else {
                httpRequest.setAttribute("authFailReason", "invalid_token");
                return false;
            }
        } catch (Exception e) {
            // 验证过程中抛异常（如签名错误、过期等）
            log.warn("JwtAuthcFilter:{}", e.getMessage());
            httpRequest.setAttribute("authFailReason", "invalid_token");
            if (e.getClass().equals(AccountBaned.class)) {
                httpRequest.setAttribute("authFailReason", "account_baned");
            }
            return false;
        }
    }

    // 封装响应方法
    private void writeJsonResponse(ServletResponse response, Result result, int statusCode) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(statusCode);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().write(JSONObject.toJSONString(result));
    }
}
