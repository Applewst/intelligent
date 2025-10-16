package com.web.api.service.impl;

import com.web.api.mapper.GraduatesMapper;
import com.web.api.pojo.Result;
import com.web.api.service.GraduatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraduatesServiceImpl implements GraduatesService {
    @Autowired
    private GraduatesMapper graduatesMapper;

    @Override
    public Result getGraduates() {
        return Result.success(graduatesMapper.getGraduates());
    }
}
