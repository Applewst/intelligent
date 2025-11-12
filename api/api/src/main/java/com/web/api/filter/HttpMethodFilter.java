package com.web.api.filter;

import com.alibaba.fastjson2.JSONObject;
import com.web.api.config.impl.JwtToken;
import com.web.api.config.impl.JwtTokenManager;
import com.web.api.error.Error;
import com.web.api.exception.AccountBanedException;
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
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import java.io.IOException;
import java.util.Set;

/**
 * 过滤器: 角色与请求方法双重过滤器
 * @author Askr-Yggdrasill
 */
@Slf4j
public class HttpMethodFilter extends AccessControlFilter {

    //默认允许的HTTP方法
    private String[] methods = {"GET"};

    //默认角色
    private String[] roles = {"user"};

    private JwtTokenManager jwtTokenManager;

    /**
     * 设置允许的HTTP方法
     * @param method 逗号分隔的HTTP方法字符串
     */
    public void setAllowedMethod(String method, JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;

        if(method != null && !method.trim().isEmpty()){
            this.methods = method.trim().split(",");
            for (int i = 0; i < this.methods.length; i++) {
                this.methods[i] = this.methods[i].trim().toUpperCase();
            }
        }
    }

    /**
     * 获取允许的角色
     * @param role 逗号分隔的角色字符串
     */
    public void setAllowedRole(String role, JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;

        if (role != null && !role.trim().isEmpty()) {
            this.roles = role.trim().split(",");
            for (int i = 0; i < this.roles.length; i++) {
                this.roles[i] = this.roles[i].trim();
            }
        }
    }

    /**
     * 检查请求的HTTP方法是否被允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        //判断 当前用户身份是否拥有允许的角色

        //判断当前请求头中是否带有jwtToken的字符串
        String jwtToken = WebUtils.toHttp(request).getHeader("token");
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        // 1.1. 没有 token
        if (jwtToken == null || jwtToken.isEmpty()) {
            // 设置拒绝原因，便于 onAccessDenied 使用
            httpRequest.setAttribute("authFailReason", "missing_token");
            return false;
        }

        // 1.2. 有 token，验证
        try {
            if (jwtTokenManager.isVerifyToken(jwtToken)) {
                //登录
                Subject subject = getSubject(request, response);
                //解析用户名
                Claims claims = jwtTokenManager.decodeToken(jwtToken);
                String userJson = (String) claims.get("user.sql");
                JwtData data = JSONObject.parseObject(userJson, JwtData.class);
                String username = data.getUsername();
                if (subject.getPrincipal() == null) {
                    //这里用自定义的 JwtToken
                    AuthenticationToken token = new JwtToken(username);
                    subject.login(token);
                }
                //判断是否拥有对应角色
                if (roles != null && roles.length != 0) {
                    Set<String> role = CollectionUtils.asSet(roles);
                    boolean hasRole = subject.hasAllRoles(role);
                    if (hasRole) {
                        return true;
                    } else {
                        httpRequest.setAttribute("authFailReason", "no_role");
                        return false;
                    }
                }
                //无权限要求时，放行，判断请求方法
            } else {
                httpRequest.setAttribute("authFailReason", "invalid_token");
                return false;
            }
        } catch (Exception e) {
            // 验证过程中抛异常（如签名错误、过期等）
            log.warn("JwtAuthcFilter:{}", e.getMessage());
            httpRequest.setAttribute("authFailReason", "invalid_token");
            if (e.getClass().equals(AccountBanedException.class)) {
                httpRequest.setAttribute("authFailReason", "account_baned");
            }
            return false;
        }

        // 2检查 HTTP 方法
        //2.1 获取请求方法
        String reqMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
        //2.2 检查方法是否被允许
        for (String m : this.methods) {
            if (m.equals(reqMethod)) {
                return true;
            }
        }
        //方法不被允许
        httpRequest.setAttribute("authFailReason", "invalid_method");
        return false;
    }

    /**
     * 访问拒绝时执行的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String reason = (String) httpRequest.getAttribute("authFailReason");

        String msg;
        int code;
        if ("invalid_token".equals(reason)) {
            msg = com.web.api.error.Error.NOACCOUNT.toString();
            code = HttpServletResponse.SC_UNAUTHORIZED;
        } else if ("account_baned".equals(reason)) {
            msg = com.web.api.error.Error.ACCOUNTBAN.toString();
            code = HttpServletResponse.SC_FORBIDDEN;
        } else if ("missing_token".equals(reason)) {
            msg = Error.NOTOKEN.toString();
            code = HttpServletResponse.SC_UNAUTHORIZED;
        } else {
            msg = Error.NOACCOUNT.toString();
            code = HttpServletResponse.SC_FORBIDDEN;
        }
        //如果没有，返回响应
        Result result = Result.error(msg);
        writeJsonResponse(response, result, code);
        return false;
    }

    // 封装响应方法
    // 封装响应方法
    private void writeJsonResponse(ServletResponse response, Result result, int statusCode) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(statusCode);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().write(JSONObject.toJSONString(result));
    }
}
