package com.web.api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.web.api.config.impl.JwtTokenManager;
import com.web.api.pojo.JwtData;
import com.web.api.pojo.LoginVo;
import com.web.api.pojo.Result;
import com.web.api.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务实现类
 */
@Slf4j
@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public Result login(LoginVo loginVo) {

        String jwtToken = null;
        try {
            //登录
            UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUsername(), loginVo.getPassword());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            //登陆成功后颁发jwt令牌
            //在realm中SimpleAuthenticationInfo设置principal为用户名
            String username = (String) subject.getPrincipal();
            String sessionId = (String) subject.getSession().getId();

            Map<String,Object> claim = new HashMap<>();
            //转换json cxe3d4
            JwtData jwtData = userServiceImpl.findIdUserNameRoleByName(username);
            claim.put("user", JSONObject.toJSONString(jwtData));
            jwtToken = jwtTokenManager.issuedToken("system", subject.getSession().getTimeout(), sessionId, claim);
        } catch (Exception e) {
            log.warn("用户{}登录失败: {}",loginVo.getUsername(), e.getMessage());
            return Result.error("登录失败,账号或密码错误");
        }
        return Result.success(jwtToken);
    }
}
