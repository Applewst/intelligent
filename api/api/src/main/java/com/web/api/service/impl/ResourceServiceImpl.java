package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.ResourceMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;
import com.web.api.service.ResourceService;
import com.web.api.util.ShortIDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 资源服务实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 分页查询资源列表
     *
     * @param pageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        // 设置分页
        PageHelper.startPage(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        // 查询资源列表
        Page<Resource> page = resourceMapper.pageQuery(pageQueryDTO);
        // 返回分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 更新资源信息
     *
     * @param resource 资源信息
     */
    @Override
    public void updateResource(Resource resource) {
        // 更新时间
        resource.setUpdateTime(LocalDateTime.now());
        // 更新资源信息
        resourceMapper.updateByID(resource);
    }

    /**
     * 根据ID获取资源信息
     *
     * @param id 资源ID
     * @return 资源信息
     */
    @Override
    public Resource getResourceById(String id) {
        return resourceMapper.getByID(id);
    }

    /**
     * 根据ID删除资源
     *
     * @param id 资源ID
     */
    @Override
    public void deleteResourceById(String id) {
        resourceMapper.deleteByID(id);
    }

    /**
     * 保存资源信息
     *
     * @param resource 资源信息
     */
    @Override
    public void saveResource(Resource resource) {
        // 生成文件唯一ID
        String shortID = ShortIDUtil.getShortID();
        resource.setId(shortID);
        // 设置更新时间
        resource.setUpdateTime(LocalDateTime.now());
        resourceMapper.save(resource);
    }
}
