package com.web.api.mapper;

import com.web.api.pojo.Graduate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 毕业生数据交互
 */
@Mapper
public interface GraduatesMapper {

    /**
     * 查询所有毕业生
     */
    @Select("select * from graduates")
    List<Graduate> getAllGraduates();
}
