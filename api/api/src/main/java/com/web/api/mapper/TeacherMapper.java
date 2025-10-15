package com.web.api.mapper;

import com.web.api.pojo.SearchDirection;
import com.web.api.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 教师详情
 */
@Mapper
public interface TeacherMapper {
    @Select(" select * from teacher")
    List<Teacher> getTeacherList();
}
