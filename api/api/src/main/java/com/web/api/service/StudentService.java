package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Student;

import java.util.List;

/**
 * 在读学生服务接口
 */
public interface StudentService {

    /**
     * 分页查询在读学生信息
     *
     * @param pageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    /**
     * 新增在读学生信息
     *
     * @param student 在读学生信息
     */
    void save(Student student);

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
    void deleteBatchByIds(List<Integer> ids);
}
