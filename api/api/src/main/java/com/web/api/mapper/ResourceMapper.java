package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件资源数据库操作
 */
@Mapper
public interface ResourceMapper {
    /**
     * 分页查询资源列表
     *
     * @param pageQueryDTO 分页查询参数
     * @return 资源列表
     */
    Page<Resource> pageQuery(PageQueryDTO pageQueryDTO);
}
