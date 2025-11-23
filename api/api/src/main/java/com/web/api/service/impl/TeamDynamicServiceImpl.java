package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.TeamDynamicMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.TeamDynamic;
import com.web.api.service.TeamDynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 团队动态服务实现类
 */
@Slf4j
@Service
public class TeamDynamicServiceImpl implements TeamDynamicService {

    @Autowired
    private TeamDynamicMapper teamDynamicMapper;

    @Override
    public List<TeamDynamic> getAllDynamics() {
        log.info("查询所有团队动态信息");
        return teamDynamicMapper.getAllDynamics();
    }

    @Override
    public List<TeamDynamic> newDynamics(int num) {
        log.info("查询最新的{}条团队动态信息", num);
        return teamDynamicMapper.getNewDynamic(num);
    }

    @Override
    public void updateDynamics(TeamDynamic teamDynamic) {
        log.info("修改团队动态");
        teamDynamicMapper.updateTeamDynamic(teamDynamic);
    }

    @Override
    public void deleteDynamics(int id) {
        log.info("删除团队动态");
        teamDynamicMapper.deleteTeamDynamic(id);
    }

    @Override
    public void saveDynamics(TeamDynamic teamDynamic) {
        log.info("新增团队动态");
        teamDynamic.setTime(LocalDateTime.now());
        teamDynamicMapper.saveTeamDynamics(teamDynamic);
    }

    @Override
    public PageResult pageQuery(int pageNum, int pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TeamDynamic> page = teamDynamicMapper.pageQuery(title);
        log.info("返回分页结果");
        return new PageResult(page.getTotal(), page.getResult());
    }
}
