package com.web.api.service.impl;

import com.web.api.mapper.TeamDynamicMapper;
import com.web.api.pojo.TeamDynamic;
import com.web.api.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 团队动态服务实现类
 */
@Slf4j
@Service
public class TeamDynamicServiceImpl implements DynamicService<TeamDynamic> {

    @Autowired
    private TeamDynamicMapper teamDynamicMapper;

    /**
     * 获取所有团队动态图片
     */
    public List<String> getAllImgs() {
        log.info("查询所有团队动态图片");
        return teamDynamicMapper.getAllImgs();
    }

    @Override
    public List<TeamDynamic> getAllDynamics() {
        log.info("查询所有团队动态信息");
        return teamDynamicMapper.getAllDynamics();
    }

    @Override
    public List<TeamDynamic> NewDynamics(int num) {
        log.info("查询最新的{}条团队动态信息", num);
        return teamDynamicMapper.getNewDynamic(num);
    }

}
