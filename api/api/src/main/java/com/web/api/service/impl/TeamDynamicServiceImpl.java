package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.TeamDynamicMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Student;
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
    public List<String> getAllImgs(int num) {
        log.info("查询最新{}条团队动态图片",num);
        return teamDynamicMapper.getNewImgs(num);
    }

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
    public void updateDynamics(int id,String title,String image,String detail,String time) {
        log.info("修改团队动态");
        teamDynamicMapper.updateTeamDynamic(id,title,image,detail,time);
    }

    @Override
    public void deleteDynamics(int id) {
    log.info("删除团队动态");
    teamDynamicMapper.deleteTeamDynamic(id);
    }

    @Override
    public void saveDynamics(String image, String title, String detail) {
    log.info("新增团队动态");
    teamDynamicMapper.saveTeamDynamics(image, title, detail);
    }

    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize,String title) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TeamDynamic> page = teamDynamicMapper.pageQuery();
        log.info("返回分页结果");
        return new PageResult(page.getTotal(), page.getResult());
    }
}
