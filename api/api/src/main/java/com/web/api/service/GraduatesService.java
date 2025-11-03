package com.web.api.service;

import com.web.api.pojo.Graduate;
import com.web.api.pojo.PageResult;

import java.util.List;

/**
 * 毕业生服务接口
 */
public interface GraduatesService {

    /**
     * 分页查询毕业生信息
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult pageQuery(Integer pageNum, Integer pageSize);

    /**
     * 保存毕业生信息
     *
     * @param graduate 毕业生信息
     */
    void save(Graduate graduate);

    /**
     * 根据ID更新毕业生信息
     *
     * @param graduate 毕业生信息
     */
    void updateById(Graduate graduate);

    /**
     * 根据ID批量删除毕业生信息
     *
     * @param ids 毕业生ID列表
     */
    void deleteBatchByIds(List<Integer> ids);
}
