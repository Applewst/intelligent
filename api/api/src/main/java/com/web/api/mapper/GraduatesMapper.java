package com.web.api.mapper;

import com.web.api.pojo.graduates;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GraduatesMapper {
    @Select("select * from graduates")
    List<graduates> getGraduates();
}
