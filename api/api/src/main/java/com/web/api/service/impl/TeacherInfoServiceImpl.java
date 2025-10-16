package com.web.api.service.impl;

import com.web.api.mapper.TeacherInfoMapper;
import com.web.api.pojo.Result;
import com.web.api.pojo.TeacherInfo;
import com.web.api.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {
   @Autowired
   private TeacherInfoMapper teacherInfoMapper;

    @Override
    public Result getTeacherInfo(int id) {
        if(teacherInfoMapper.getTeacherInfo(id)==null){
            return Result.error("教师不存在");
        }
        return Result.success(teacherInfoMapper.getTeacherInfo(id));
    }
}
