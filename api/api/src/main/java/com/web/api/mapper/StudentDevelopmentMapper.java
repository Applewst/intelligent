package com.web.api.mapper;

import com.web.api.pojo.StudentDevelopment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 学生成长数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface StudentDevelopmentMapper {

    List<StudentDevelopment> selectAllStudentDevelopment(String name);

    void addStudentDevelopment(StudentDevelopment studentDevelopment);

    void motifyStudentDevelopmentById(StudentDevelopment studentDevelopment);

    void deleteStudentDevelopmentById(int id);
}
