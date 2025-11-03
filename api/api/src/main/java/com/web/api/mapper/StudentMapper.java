package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 在读学生数据交互
 */
@Mapper
public interface StudentMapper {

    /**
     * 分页查询在读学生信息
     *
     * @return 在读学生列表
     */
    Page<Student> pageQuery();

    /**
     * 新增在读学生信息
     *
     * @param student 在读学生信息
     */
    @Insert("insert into student(name, grade, stucode, gender, avatar) " +
            "values (#{name}, #{grade}, #{stucode}, #{gender}, #{avatar})")
    void insert(Student student);

    /**
     * 修改在读学生信息
     *
     * @param student 在读学生信息
     */
    void updateById(Student student);

    /**
     * 根据ID删除批量在读学生信息
     *
     * @param ids 在读学生ID列表
     */
    void deleteBatch(List<Integer> ids);
}
