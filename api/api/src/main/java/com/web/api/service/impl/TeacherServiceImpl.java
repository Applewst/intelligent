package com.web.api.service.impl;

import com.web.api.mapper.TeacherMapper;
import com.web.api.pojo.Result;
import com.web.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService{
   @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Result getTeacher() { return Result.success(teacherMapper.getTeacherList());}
}
