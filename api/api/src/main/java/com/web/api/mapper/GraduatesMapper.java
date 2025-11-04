package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.Graduate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 毕业生数据交互
 */
@Mapper
public interface GraduatesMapper {
    /**
     * 分页查询毕业生信息
     *
     * @return 毕业生列表
     */
    @Select("select id,name,gender,avatar from graduates")
    Page<Graduate> pageQuery();

    /**
     * 插入毕业生信息
     *
     * @param graduate 毕业生信息
     */
    @Insert("insert into graduates(name,gender,avatar) values(#{name},#{gender},#{avatar})")
    void insert(Graduate graduate);

    /**
     * 更新毕业生信息
     *
     * @param graduate 毕业生信息
     */
    void update(Graduate graduate);

    /**
     * 根据ID批量删除毕业生信息
     *
     * @param ids 毕业生ID列表
     */
    void deleteBatch(List<Integer> ids);
}
