package com.web.api.service;

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

    void updateDynamics(int id,String title,String image,String detail,String time);

    /**
     * 删除动态
     * @param
     */

    void deleteDynamics(int id);

    /**
     *新增动态
     */

    void saveDynamics(String image, String title, String detail);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult pageQuery(Integer pageNum, Integer pageSize,String title);
}
