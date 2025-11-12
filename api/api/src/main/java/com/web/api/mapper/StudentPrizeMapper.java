package com.web.api.mapper;

import com.web.api.pojo.StudentPrize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生获奖数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface StudentPrizeMapper {

    /**
     * 查询所有学生获奖信息
     * @return 学生获奖列表
     */
    List<StudentPrize> getAllStudentPrizes(String name);

    /**
     * 添加学生获奖信息
     * @param studentPrize 学生获奖信息
     */
    void addStudentPrize(StudentPrize studentPrize);

    /**
     * 修改学生获奖信息
     * @param studentPrize 学生获奖信息
     */
    void motifyStudentPrizeById(StudentPrize studentPrize);

    /**
     * 删除学生获奖信息
     * @param id 学生获奖ID
     */
    void deleteStudentPrizeById(int id);
}
