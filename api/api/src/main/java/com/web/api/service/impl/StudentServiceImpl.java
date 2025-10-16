package com.web.api.service.impl;

import com.web.api.mapper.StudentMapper;
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

    @Override
    public List<Student> getAllStudents() {
        log.info("查询所有学生信息");
        return studentMapper.getAllStudents();
    }
}
