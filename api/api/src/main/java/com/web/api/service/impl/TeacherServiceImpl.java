package com.web.api.service.impl;

import com.web.api.mapper.TeacherMapper;
import com.web.api.pojo.Result;
import com.web.api.pojo.Teacher;
import com.web.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师服务实现类
 */
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService{
   @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getAllTeachers() {
        log.info("查询所有教师信息");
        return teacherMapper.getAllTeachers();
    }

    @Override
    public Teacher getTeacherById(int id) {
        log.info("根据ID'{}'查询教师信息", id);
        return teacherMapper.getTeacherById(id);
    }
}
