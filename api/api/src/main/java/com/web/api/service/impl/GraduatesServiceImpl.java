package com.web.api.service.impl;

import com.web.api.mapper.GraduatesMapper;
import com.web.api.pojo.Graduate;
import com.web.api.pojo.Result;
import com.web.api.service.GraduatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 毕业生服务实现类
 */
@Slf4j
@Service
public class GraduatesServiceImpl implements GraduatesService {
    @Autowired
    private GraduatesMapper graduatesMapper;

    @Override
    public List<Graduate> getAllGraduates() {
        log.info("查询所有毕业生信息");
        return graduatesMapper.getAllGraduates();
    }
}
