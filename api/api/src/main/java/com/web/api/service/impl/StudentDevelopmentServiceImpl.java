package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.StudentDevelopmentMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.StudentDevelopment;
import com.web.api.service.StudentDevelopmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生成长业务逻辑实现
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class StudentDevelopmentServiceImpl implements StudentDevelopmentService {

    @Autowired
    private StudentDevelopmentMapper studentDevelopmentMapper;

    @Override
    public PageResult getAllStudentDevelopment(PageQueryDTO pageQuery) {
        //1.设置分页参数
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        //2.执行查询,转为Page格式
        List<StudentDevelopment> empList = studentDevelopmentMapper.selectAllStudentDevelopment(pageQuery.getName());
        Page<StudentDevelopment> p = (Page<StudentDevelopment>) empList;
        //3.返回分页结果
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void addStudentDevelopment(StudentDevelopment studentDevelopment) {
        studentDevelopment.setTime(LocalDateTime.now());
        try {
            studentDevelopmentMapper.addStudentDevelopment(studentDevelopment);
        } catch (Exception e) {
            log.error("添加学生成长信息时发生异常: {}", e.getMessage());
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void motifyStudentDevelopmentById(StudentDevelopment studentDevelopment) {
        if (studentDevelopment.getId() == null) {
            log.warn("修改学生成长信息时发生异常: ID不能为空");
            throw new NoIdException();
        }
        try {
            studentDevelopmentMapper.motifyStudentDevelopmentById(studentDevelopment);
        } catch (Exception e) {
            log.error("修改学生成长信息时发生异常: {}", e.getMessage());
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deleteStudentDevelopmentById(Integer id) {
        if (id == null) {
            log.warn("删除学生成长信息时发生异常: ID不能为空");
            throw new NoIdException();
        }
        try {
            studentDevelopmentMapper.deleteStudentDevelopmentById(id);
        } catch (Exception e) {
            log.error("删除学生成长信息时发生异常: {}", e.getMessage());
            throw new DatabaseOperationException();
        }
    }
}
