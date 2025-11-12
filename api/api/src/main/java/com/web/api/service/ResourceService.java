package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;

/**
 * 资源服务类
 */
public interface ResourceService {

    /**
     * 分页查询资源列表
     *
     * @param pageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    /**
     * 更新资源信息
     *
     * @param resource 资源信息
     */
    void updateResource(Resource resource);

    /**
     * 根据ID获取资源信息
     *
     * @param id 资源ID
     * @return 资源信息
     */
    Resource getResourceById(String id);

    /**
     * 根据ID删除资源
     *
     * @param id 资源ID
     */
    void deleteResourceById(String id);

    /**
     * 保存资源信息
     *
     * @param resource 资源信息
     */
    void saveResource(Resource resource);
}
