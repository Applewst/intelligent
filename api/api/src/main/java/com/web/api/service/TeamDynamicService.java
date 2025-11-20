package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.TeamDynamic;

import java.util.List;

public interface TeamDynamicService {
    /**
     * 获取所有动态信息
     */

    public List<TeamDynamic> getAllDynamics();
    /**.
     * 获取最新动态
     */

    public List<TeamDynamic> newDynamics(int num);

    /**
     * 更新动态
     * @param
     */

    void updateDynamics(TeamDynamic teamDynamic);

    /**
     * 删除动态
     * @param
     */

    void deleteDynamics(int id);

    /**
     *新增动态
     */

    void saveDynamics(TeamDynamic teamDynamic);

    /**
     * 分页查询
     * @return
     */
    PageResult pageQuery(int pageNum, int pageSize,String title);
}
