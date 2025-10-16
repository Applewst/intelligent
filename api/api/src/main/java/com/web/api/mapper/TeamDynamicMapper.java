package com.web.api.mapper;

import com.web.api.pojo.TeamDynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团队活动数据交互
 */
@Mapper
public interface TeamDynamicMapper {

    /**
     * 查询所有团队动态
     */
    List<TeamDynamic> getAllDynamics();

    /**
     * 查询最新{num}条动态
     */
    List<TeamDynamic> getNewDynamic(@Param("num") int num);

    /**
     * 获取所有图片链接
     */
    List<String> getAllImgs();
}
