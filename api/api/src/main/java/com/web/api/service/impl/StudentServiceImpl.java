package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.StudentMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Student;
import com.web.api.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务实现类
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询在读学生信息
     *
     * @param pageNum  当前页数
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        // 分页查询学生列表
        Page<Student> page = studentMapper.pageQuery();
        // 返回分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增在读学生信息
     *
     * @param student 在读学生信息
     */
    @Override
    public void save(Student student) {
        studentMapper.insert(student);
    }

    /**
     * 修改在读学生信息
     *
     * @param student 在读学生信息
     */
    @Override
    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    /**
     * 根据ID删除批量在读学生信息
     *
     * @param ids 在读学生ID列表
     */
    @Override
    public void deleteBatchByIds(List<Integer> ids) {
        studentMapper.deleteBatch(ids);
    }
}
