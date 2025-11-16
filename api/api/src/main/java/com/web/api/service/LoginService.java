package com.web.api.service;

import com.web.api.pojo.LoginVo;
import com.web.api.pojo.Result;

/**
 * 登录服务接口
 * @author Askr-Yggdrasill
 */
public interface LoginService {

    /**
     * 登录方法
     * @param loginVo 登录信息
     * @return jwtToken
     */
    Result login(LoginVo loginVo);
}
