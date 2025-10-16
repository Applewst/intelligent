package com.web.api.mapper;

import com.web.api.pojo.JwtData;

import com.web.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据交互
 */
@Mapper
public interface UserMapper{

    /**
     * 根据用户名查询用户
     */
    public User findUserByName(String username);

    /**
     * 根据用户名查询用户id, name, identity
     */
    public JwtData findIdNameIdentityByName(String username);

    /**
     * 添加用户
     */
    public void addUser(User user);
}
