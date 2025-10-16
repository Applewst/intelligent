package com.web.api.mapper;

import com.web.api.pojo.Result;
import com.web.api.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudenetMapper {

        @Select("select * from student")
        List<Student> getStudent();
}
