package com.web.api.controller;

import com.web.api.pojo.LoginVo;
import com.web.api.pojo.User;
import com.web.api.service.impl.LoginServiceimpl;
import com.web.api.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.web.api.pojo.Result;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginServiceimpl loginServiceimpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 登录
     */
    @GetMapping("/login")
    public Result login(LoginVo loginVo) {
        return loginServiceimpl.login(loginVo);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(User user) {
        userServiceImpl.register(user);
        return Result.success();
    }
}