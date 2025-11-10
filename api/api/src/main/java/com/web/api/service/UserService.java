package com.web.api.service;

import com.web.api.pojo.JwtData;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.User;

import java.util.List;

/**
 * 用户操作接口
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @return 用户信息
     */
    public User findUserByName(String username);

    /**
     * 根据用户名查询用户id,用户名,角色
     * @param username 用户名
     * @return JwtData 用户id,用户名,角色
     */
    public JwtData findIdUserNameRoleByName(String username);

    /**
     * 用户注册
     * @param user 用户信息
     */
    public void register(User user);

    /**
     * 获取用户列表
     * @param pageQueryDTO 分页查询参数
     * @return 用户列表
     */
    public PageResult getUserList(PageQueryDTO pageQueryDTO);

    /**
     * 根据用户ID修改用户信息
     * @param user 用户信息
     */
    public void modifyUserById(User user);

    /**
     * 根据用户ID删除用户
     * @param id 用户ID
     */
    public void deleteUserById(int id);
}
