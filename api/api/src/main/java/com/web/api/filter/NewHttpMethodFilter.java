package com.web.api.filter;

import com.alibaba.fastjson2.JSONObject;
import com.web.api.config.impl.JwtTokenManager;
import com.web.api.error.Error;
import com.web.api.pojo.Result;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import java.io.IOException;

/**
 * HTTP方法-权限复合过滤器
 * @author Askr-Yggdrasill
 */
public class NewHttpMethodFilter extends AccessControlFilter {

    // 设置各HTTP方法角色
    private String[] getRoles = {};
    private String[] postRoles = {};
    private String[] putRoles = {};
    private String[] deleteRoles = {};

    // jwt管理器
    private JwtTokenManager jwtTokenManager;

    /**
     * 初始化过滤器
     * @param getRoles    GET方法允许的角色
     * @param postRoles   POST方法允许的角色
     * @param putRoles    PUT方法允许的角色
     * @param deleteRoles DELETE方法允许的角色
     * @param jwtTokenManager JWT令牌管理器
     */
    public void setAllowedRole(String getRoles, String postRoles, String putRoles, String deleteRoles,
                               JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;

        if (getRoles != null && ! getRoles.trim().isEmpty()) {
            this.getRoles = getRoles.trim().split(",");
            for (int i = 0; i < this.getRoles.length; i++) {
                this.getRoles[i] = this.getRoles[i].trim();
            }
        }

        if (postRoles != null && !postRoles.trim().isEmpty()) {
            this.postRoles = postRoles.trim().split(",");
            for (int i = 0; i < this.postRoles.length; i++) {
                this.postRoles[i] = this.postRoles[i].trim();
            }
        }

        if (putRoles != null && !putRoles.trim().isEmpty()) {
            this.putRoles = putRoles.trim().split(",");
            for (int i = 0; i < this.putRoles.length; i++) {
                this.putRoles[i] = this.putRoles[i].trim();
            }
        }

        if (deleteRoles != null && !deleteRoles.trim().isEmpty()) {
            this.deleteRoles = deleteRoles.trim().split(",");
            for (int i = 0; i < this.deleteRoles.length; i++) {
                this.deleteRoles[i] = this.deleteRoles[i].trim();
            }
        }
    }


    /**
     * 访问控制
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        // 获取HTTP请求对象
        HttpServletRequest httpRequest = WebUtils.toHttp(request);

        // 获取请求方法
        String reqMethod = WebUtils.toHttp(request).getMethod().toUpperCase();

        // 获得当前token
        String jwtToken = WebUtils.toHttp(request).getHeader("token");

        // 获得当前用户角色
        String role = "";
        if (jwtToken != null && !jwtToken.isEmpty()) {
            try {
                role = jwtTokenManager.getCurrentUserRole(jwtToken);
            } catch (Exception e) {
                // 设置拒绝原因，便于 onAccessDenied 使用
                httpRequest.setAttribute("authFailReason", "invalid_token");
                return false;
            }
        }

        switch (reqMethod) {
            case "GET" -> {
                // 如果权限列表为空，则表示允许所有角色访问
                if (getRoles == null || getRoles.length == 0) {
                    return true;
                }
                // 否则检查角色是否在允许列表中
                for (String r : getRoles) {
                    if (r.equals(role)) {
                        return true;
                    }
                }
                // 如果没有匹配的角色，设置拒绝原因: 账号被禁用
                httpRequest.setAttribute("authFailReason", "no_permission");
            }
            case "POST" -> {
                if (postRoles == null || postRoles.length == 0) {
                    return true;
                }
                for (String r : postRoles) {
                    if (r.equals(role)) {
                        return true;
                    }
                }
                httpRequest.setAttribute("authFailReason", "no_permission");
            }
            case "PUT" -> {
                if (putRoles == null || putRoles.length == 0) {
                    return true;
                }
                for (String r : putRoles) {
                    if (r.equals(role)) {
                        return true;
                    }
                }
                httpRequest.setAttribute("authFailReason", "no_permission");
            }
            case "DELETE" -> {
                if (deleteRoles == null || deleteRoles.length == 0) {
                    return true;
                }
                for (String r : deleteRoles) {
                    if (r.equals(role)) {
                        return true;
                    }
                }
                httpRequest.setAttribute("authFailReason", "no_permission");
            }
        }
        return false;
    }

    /**
     * 验证失败后的处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String reason = (String) httpRequest.getAttribute("authFailReason");

        String msg;
        int code;
        //token无效
        if ("invalid_token".equals(reason)) {
            msg = Error.NOACCOUNT.toString();
            code = HttpServletResponse.SC_UNAUTHORIZED;
            //账号不存在或已被禁用
        } else if ("account_baned".equals(reason)) {
            msg = Error.ACCOUNTBAN.toString();
            code = HttpServletResponse.SC_FORBIDDEN;
            //权限不足
        } else if ("no_permission".equals(reason)) {
            msg = Error.NOPERMISSION.toString();
            code = HttpServletResponse.SC_FORBIDDEN;
            //其他情况一律视为未登录
        } else {
            msg = Error.NOACCOUNT.toString();
            code = HttpServletResponse.SC_FORBIDDEN;
        }
        //如果没有，返回响应
        Result result = Result.error(msg);
        writeJsonResponse(response, result, code);
        return false;
    }

    /**
     * 封装响应方法
     * @param response   响应对象
     * @param result     响应结果
     * @param statusCode 状态码
     * @throws IOException IO异常
     */
    private void writeJsonResponse(ServletResponse response, Result result, int statusCode) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(statusCode);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().write(JSONObject.toJSONString(result));
    }
}
