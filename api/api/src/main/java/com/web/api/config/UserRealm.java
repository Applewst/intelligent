package com.web.api.config;

import com.web.api.config.impl.JwtToken;
import com.web.api.config.impl.MultiTypeCredentialsMatcher;
import com.web.api.pojo.User;
import com.web.api.service.impl.UserServiceImpl;
import com.web.api.util.DigestsUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Askr-Yggdrasill
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * 判断此 Realm 是否支持此 Token
     * @param token token
     * @return boolean
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 支持两种 Token：账号密码 和 JWT
        return token instanceof UsernamePasswordToken ||
                token instanceof JwtToken;
    }

    /**
     * 授权
     * @param principalCollection 主体集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //查询用户
        User user = userServiceImpl.findUserByName(username);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //身份
        simpleAuthorizationInfo.addRole(user.getIdentity());
        //权限
        simpleAuthorizationInfo.addStringPermission(user.getIdentity()+":*");
        //返回授权信息
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken 认证令牌
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //使用jwt登录
        if (authenticationToken instanceof JwtToken) {
            String username = (String) authenticationToken.getPrincipal();
            return new SimpleAuthenticationInfo(username, "", getName());
        }
        //获取用户名
        String username = (String) authenticationToken.getPrincipal();
        //获取用户
        User user = userServiceImpl.findUserByName(username);
        log.info("用户: {} 正在登录", username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (user.isDisabled()){
            throw new DisabledAccountException("账号已被禁用");
        }
        //构建认证信息
        String password = user.getPassword();
        String salt = user.getSalt();
        //返回认证信息
        return new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(salt), getName());
    }

    /**
     * 构建密码比较器
     */
    @PostConstruct  //初始化后自动执行
    public void initCredentialsMatcher() {
        // 创建自定义的、支持多种 Token 类型的匹配器
        MultiTypeCredentialsMatcher customMatcher = new MultiTypeCredentialsMatcher();
        // 为匹配器设置用于密码验证的哈希算法和迭代次数
        customMatcher.setHashAlgorithmName(DigestsUtil.SHA256);
        customMatcher.setHashIterations(DigestsUtil.ITERATIONS);
        // 生效密码比较器
        setCredentialsMatcher(customMatcher);
    }
}
























