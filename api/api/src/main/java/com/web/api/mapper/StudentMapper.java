package com.web.api.mapper;

import com.web.api.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 在读学生数据交互
 */
@Mapper
public interface StudentMapper {

        /**
         * 查询所有在读学生
         */
        List<Student> getAllStudents();
}
