package com.web.api.service.impl;

import com.web.api.mapper.StudenetMapper;
import com.web.api.pojo.Result;
import com.web.api.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudenetMapper studenetMapper;

    @Override
    public Result getStudent() {
        return Result.success(studenetMapper.getStudent());
    }
}
