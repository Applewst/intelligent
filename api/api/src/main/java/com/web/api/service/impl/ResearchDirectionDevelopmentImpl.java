package com.web.api.service.impl;

import com.web.api.mapper.ResearchDirectionDevelopmentMapper;
import com.web.api.pojo.Result;
import com.web.api.service.ResearchDirectionDevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResearchDirectionDevelopmentImpl implements ResearchDirectionDevelopmentService {
    @Autowired
    private ResearchDirectionDevelopmentMapper researchDirectionMapper;

    @Override
    public Result searchDirectionDevelopment() {
        return Result.success(researchDirectionMapper);
    }
}
