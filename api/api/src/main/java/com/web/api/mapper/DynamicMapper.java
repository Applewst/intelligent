package com.web.api.mapper;

import com.web.api.pojo.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 动态数据交互
 */
@Mapper
public interface DynamicMapper {

    /**
     * 查询最新4条动态
     */
    @Select("select * from dynamic order by id desc limit 4")
    public List<Dynamic> selectNewDynamic();

}
