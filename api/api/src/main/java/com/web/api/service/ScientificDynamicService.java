package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ScientificDynamic;

import java.util.List;

public interface ScientificDynamicService {
    /**
     * 获取所有动态信息
     */

    public List<ScientificDynamic> getAllDynamics();
    /**.
     * 获取最新动态
     */

    public List<ScientificDynamic> newDynamics(int num);

    /**
     * 更新动态
     * @param
     */

    void updateDynamics(ScientificDynamic scientificDynamic);

    /**
     * 删除动态
     * @param
     */

    void deleteDynamics(Integer id);

    /**
     *新增动态
     */

    void saveDynamics(ScientificDynamic scientificDynamic);

    /**
     * 分页查询
     * @return
     */
    PageResult pageQuery(int pageNum, int pageSize,String title);
}
