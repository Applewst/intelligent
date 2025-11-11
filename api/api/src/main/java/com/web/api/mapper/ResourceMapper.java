package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 保存资源信息
     *
     * @param resource 资源信息
     */
    @Insert("insert into resource (id,name, type, size, url, update_time) " +
            "values (#{id},#{name}, #{type}, #{size}, #{url}, #{updateTime})")
    void save(Resource resource);

    /**
     * 更新资源信息
     *
     * @param resource 资源信息
     */
    void updateByID(Resource resource);

    /**
     * 根据ID获取资源信息
     *
     * @param id 资源ID
     * @return 资源信息
     */
    @Select("select * from resource where id = #{id}")
    Resource getByID(String id);

    /**
     * 根据ID删除资源
     *
     * @param id 资源ID
     */
    @Delete("delete from resource where id = #{id}")
    void deleteByID(String id);
}
