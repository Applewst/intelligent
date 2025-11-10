package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoFindException;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.UserMapper;
import com.web.api.pojo.JwtData;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.service.UserService;
import com.web.api.util.DigestsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.api.pojo.User;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 * @author Askr-Yggdrasill
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
        log.info("用户'{}'注册中...", user.getUsername());
        //检查用户名是否已存在
        if (userMapper.findUserByName(user.getUsername()) != null) {
            log.warn("用户名'{}'已存在，注册失败", user.getUsername());
            throw new RuntimeException("用户名已存在");
        }

        //加密密码
        Map<String,String> newPassword;
        newPassword = DigestsUtil.encryptPassword(user.getPassword());

        //创建新用户对象
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(newPassword.get("password"));
        newUser.setSalt(newPassword.get("salt"));
        newUser.setIdentity(user.getIdentity());
        //设置账户为启用
        newUser.setDisabled(false);

        try {
            userMapper.addUser(newUser);
        } catch (Exception e) {
            log.error("用户{}注册失败: ", newUser.getUsername(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public PageResult getUserList(PageQueryDTO pageQueryDTO) {
        //1.设置分页参数
        PageHelper.startPage(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        //2.执行查询,转为Page格式
        List<User> empList = userMapper.getAllUser(pageQueryDTO.getName());
        Page<User> p = (Page<User>) empList;
        //3.返回分页结果
        return new PageResult((long) p.getPages(),p.getResult());
    }

    @Override
    public void modifyUserById(User user) {
        if (user.getId() == null) {
            log.warn("身份不能为空，修改失败");
            throw new NoIdException();
        }
        //检查用户是否存在
        if (!userMapper.findUserById(user.getId())) {
            log.warn("用户ID:" + user.getId() + "不存在，修改失败");
            throw new NoFindException();
        }
        //当前密码不为空时，说明需要修改密码
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // 重新加密密码
            Map<String, String> newPassword = DigestsUtil.encryptPassword(user.getPassword());
            user.setPassword(newPassword.get("password"));
            user.setSalt(newPassword.get("salt"));
        }
        try {
            userMapper.modifyUserById(user);
        } catch (Exception e) {
            log.error("用户{}信息修改失败:", user.getUsername(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deleteUserById(int id) {
        if (!userMapper.findUserById(id)) {
            log.warn("用户ID" + id + "不存在，删除失败");
            throw new NoFindException();
        }
        try {
            userMapper.deleteUserById(id);
            log.info("用户ID" + id + "删除成功");
        } catch (Exception e) {
            log.error("用户ID{}删除失败:", id, e);
            throw new DatabaseOperationException();
        }
    }
}











