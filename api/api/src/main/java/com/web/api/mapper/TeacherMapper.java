package com.web.api.mapper;

import com.web.api.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 教师数据交互
 */
@Mapper
public interface TeacherMapper {

    /**
     * 查询所有教师
     */
    List<Teacher> getAllTeachers();

    /**
     * 根据ID查询教师
     */
    Teacher getTeacherById(@Param("id") int id);
}
