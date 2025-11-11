package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.ResourceMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;
import com.web.api.service.ResourceService;
import com.web.api.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问URL
     */
    @Override
    public String uploadFile(MultipartFile file, ResourceDTO resourceDTO) {
        // 文件存储逻辑
        try {
            String fileUrl = FileUtil.uploadFile(file);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败", e);
        }
        resourceMapper.save(resourceDTO);
        return fileUrl;
    }
}
