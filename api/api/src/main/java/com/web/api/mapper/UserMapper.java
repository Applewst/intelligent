package com.web.api.mapper;

import com.web.api.pojo.JwtData;

import com.web.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户数据交互
 */
@Mapper
public interface UserMapper{

    /**
     * 根据用户id查询用户是否存在
     * @param id 用户ID
     * @return 是否存在
     */
    public boolean findUserById(int id);

    /**
     * 根据用户名查询用户
     */
    public User findUserByName(String username);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return JwtData 用户id, name, identity
     */
    public JwtData findIdNameIdentityByName(String username);

    /**
     * 添加用户
     * @param user 用户信息
     */
    public void addUser(User user);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    public List<User> getAllUser();

    /**
     * 根据用户ID修改用户信息
     * @param user 用户信息
     */
    public void modifyUserById(User user);

    /**
     * 根据用户id删除用户
     * @param id 用户ID
     */
    public void deleteUserById(int id);
}
