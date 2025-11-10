package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.User;
import com.web.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.web.api.pojo.Result;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页获取用户列表
     * @param pageQueryDTO 分页查询参数
     * @return 用户列表
     */
    @GetMapping("/list")
    public Result getUserList(PageQueryDTO pageQueryDTO) {
        return Result.success(userService.getUserList(pageQueryDTO));
    }

    /**
     * 注册用户
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success("用户注册成功");
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改结果
     */
    @PutMapping("")
    public Result modifyUser(@RequestBody User user) {
        userService.modifyUserById(user);
        return Result.success("用户信息修改成功");
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("")
    public Result deleteUser(int id) {
        userService.deleteUserById(id);
        return Result.success("用户删除成功");
    }
}