package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Teacher;

import java.util.List;

/**
 * 教师详情接口
 */
public interface TeacherService {

    /**
     * 教师分页查询
     *
     * @param pageQueryDTO 分页查询参数
     * @return 分页结果
     */
    PageResult page(PageQueryDTO pageQueryDTO);

    /**
     * 根据ID查询教师
     *
     * @param id 教师ID
     * @return 教师信息
     */
    Teacher getById(Integer id);

    /**
     * 保存教师信息
     *
     * @param teacher 教师信息
     */
    void save(Teacher teacher);

    /**
     * 根据ID更新教师信息
     *
     * @param teacher 教师信息
     */
    void updateById(Teacher teacher);

    /**
     * 根据ID删除教师
     *
     * @param ids 教师ID列表
     */
    void deleteBatchByIds(List<Integer> ids);
}
