package com.web.api.mapper;

import com.web.api.pojo.TeacherInfo;
import com.web.api.pojo.graduates;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherInfoMapper {
    @Select("select *from ")
    List<TeacherInfo> getTeacherInfo();
}
