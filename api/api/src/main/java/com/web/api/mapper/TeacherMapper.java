package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 教师数据交互
 */
@Mapper
public interface TeacherMapper {

    /**
     * 分页查询教师
     *
     * @return 教师分页数据
     */
    Page<Teacher> page();

    /**
     * 根据ID查询教师
     *
     * @param id 教师ID
     * @return 教师信息
     */
    @Select("SELECT name, title, email,avatar,detail,gender FROM teacher WHERE id = #{id}")
    Teacher getById(Integer id);

    /**
     * 插入教师信息
     *
     * @param teacher 教师信息
     */
    void insert(Teacher teacher);

    /**
     * 更新教师信息
     *
     * @param teacher 教师信息
     */
    void update(Teacher teacher);

    /**
     * 批量删除教师
     *
     * @param ids 教师ID列表
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 获取所有教师信息
     *
     * @return 教师列表
     */
    @Select("SELECT id, name, title, email, avatar, detail FROM teacher")
    List<Teacher> getAllTeachers();
}
