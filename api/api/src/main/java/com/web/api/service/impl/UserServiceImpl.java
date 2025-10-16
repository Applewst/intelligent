package com.web.api.service.impl;

import com.web.api.mapper.UserMapper;
import com.web.api.pojo.JwtData;
import com.web.api.service.UserService;
import com.web.api.util.DigestsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.api.pojo.User;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public JwtData findIdUserNameRoleByName(String username) {
        log.info("根据用户名'{}'查询用户ID,用户名,身份", username);
        return userMapper.findIdNameIdentityByName(username);
    }

    @Override
    public void register(User user) {
        Map<String,String> newPassword = new HashMap<>();
        newPassword = DigestsUtil.encryptPassword(user.getPassword());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(newPassword.get("password"));
        newUser.setSalt(newPassword.get("salt"));
        newUser.setIdentity(user.getIdentity());
        newUser.setDisabled(false); //默认启用

        try {
            userMapper.addUser(newUser);
        } catch (Exception e) {
            log.warn("用户" + newUser.getUsername() + "登录失败: " + e.getMessage());
            throw new RuntimeException("注册失败" + e.getMessage());
        }
    }

}











