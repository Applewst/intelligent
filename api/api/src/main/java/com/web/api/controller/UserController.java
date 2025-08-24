package com.web.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pojo.Result;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 登录
     */
    @GetMapping("/login")
    public Result login() {
        return new Result();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register() {
        return new Result();
    }
}
