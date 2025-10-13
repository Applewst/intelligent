package com.web.api.service;

import com.web.api.pojo.JwtData;
import com.web.api.pojo.User;

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

    public void register(User user);
}
