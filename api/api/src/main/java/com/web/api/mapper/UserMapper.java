package com.web.api.mapper;

import com.web.api.pojo.JwtData;

import com.web.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据交互
 */
@Mapper
public interface UserMapper{

    public User findUserByName(String username);

    public JwtData findIdNameIdentityByName(String username);

    public void addUser(User user);
}
