package com.web.api.service.impl;

import com.web.api.mapper.DynamicMapper;
import com.web.api.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DynamicServiceImpl implements com.web.api.service.DynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public Result NewDynamic() {

        log.info("查询最新动态");
        return Result.success(dynamicMapper.selectNewDynamic());
    }
}
