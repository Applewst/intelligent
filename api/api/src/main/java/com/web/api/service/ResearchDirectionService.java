package com.web.api.service;

import com.web.api.pojo.ResearchDirection;
import com.web.api.pojo.Result;

import java.util.List;

/**
 * 研究方向服务接口
 */
public interface ResearchDirectionService {

    /**
     * 获取所有研究方向
     * @return 研究方向列表
     */
    public List<ResearchDirection> getAllDirection();
}
