package com.web.api.service;

import com.web.api.pojo.Graduate;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;

import java.util.List;

/**
 * 毕业生服务接口
 */
public interface GraduatesService {

    /**
     * 分页查询毕业生信息
     *
     * @param pageQueryDTO 分页查询参数
     * @return 毕业生分页结果
     */
    PageResult pageQuery(PageQueryDTO pageQueryDTO);

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
