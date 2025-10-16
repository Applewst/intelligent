package com.web.api.service;

import com.web.api.pojo.Result;

import java.util.List;

/**
    动态服务接口
 */
public interface DynamicService<T> {

    /**
     * 获取所有动态信息
     */
    public List<T> getAllDynamics();

    /**
     * 获取最新动态
     */
    public List<T> NewDynamics(int num);
}
