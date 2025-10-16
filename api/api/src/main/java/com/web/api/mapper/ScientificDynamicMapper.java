package com.web.api.mapper;

import com.web.api.pojo.ScientificDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科研动态数据交互
 */
@Mapper
public interface ScientificDynamicMapper {

    /**
     * 查询所有科研动态
     */
    public List<ScientificDynamic> getAllDynamic();

    /**
     * 查询最新4条动态
     */
    public List<ScientificDynamic> selectNewDynamics(@Param("num") int num);
}
