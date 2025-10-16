package com.web.api.service;

import com.web.api.pojo.Graduate;
import com.web.api.pojo.Result;

import java.util.List;

/**
 * 毕业生服务接口
 */
public interface GraduatesService {

    /**
     * 获取所有毕业生信息
     * @return 毕业生列表
     */
    public List<Graduate> getAllGraduates();
}
