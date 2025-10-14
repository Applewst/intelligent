package com.web.api.mapper;

import com.web.api.service.impl.SearchDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据交互
 */
@Mapper
public interface SearchDirectionMapper {

    @Select(" select * from research_direction")
    public List<SearchDirection> selectSearchDirection();
}
