package com.web.api.service.impl;

import com.web.api.mapper.ResearchDirectionMapper;
import com.web.api.pojo.ResearchDirection;
import com.web.api.service.ResearchDirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 研究方向服务实现类
 */
@Slf4j
@Service
public class ResearchDirectionServiceImpl implements ResearchDirectionService {
    @Autowired
    private ResearchDirectionMapper researchDirectionMapper;

    @Override
    public List<ResearchDirection> getAllDirection() {
        log.info("查询所有研究方向信息");
        return researchDirectionMapper.getAllDirections();
    }
}
