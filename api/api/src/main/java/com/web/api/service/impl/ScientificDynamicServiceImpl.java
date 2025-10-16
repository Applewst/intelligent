package com.web.api.service.impl;

import com.web.api.mapper.ScientificDynamicMapper;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 科研动态服务实现类
 */
@Slf4j
@Service
public class ScientificDynamicServiceImpl implements DynamicService<ScientificDynamic> {

    @Autowired
    private ScientificDynamicMapper scientificDynamicMapper;

    @Override
    public List<ScientificDynamic> getAllDynamics() {
        log.info("查询所有科研动态信息");
        return scientificDynamicMapper.getAllDynamic();
    }

    @Override
    public List<ScientificDynamic> NewDynamics(int num) {
        log.info("查询最新的{}条科研动态信息", num);
        return scientificDynamicMapper.selectNewDynamics(num);
    }
}
