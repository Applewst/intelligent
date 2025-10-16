package com.web.api.mapper;

import com.web.api.pojo.ResearchDirectionDevelopment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResearchDirectionDevelopmentMapper {
    @Select("select * from research_direction_developments")
    List<ResearchDirectionDevelopment> getResearchDirectionDevelopment();
}
