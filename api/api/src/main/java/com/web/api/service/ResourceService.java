package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传文件
     *
     * @param file        文件
     * @param resourceDTO 资源信息
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, ResourceDTO resourceDTO);
}
