package com.web.api.mapper;

import com.web.api.pojo.TeamActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamEventMapper {
    @Select("select * from teamevent")
    List<TeamActivity> getTeamEvent();
}
