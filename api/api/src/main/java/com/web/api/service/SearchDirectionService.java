package com.web.api.service;


import com.web.api.pojo.Result;
import com.web.api.service.impl.SearchDirection;

/**
 * 研究方向服务接口
 */
public interface SearchDirectionService {

    /**
     * 查询研究方向
     * @return Result
     */
    Result searchDirection();
}
