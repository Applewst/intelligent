package com.web.api.service.impl;

import com.web.api.exception.DatabaseOperationException;
import com.web.api.mapper.TeamMapper;
import com.web.api.pojo.Team;
import com.web.api.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 团队介绍服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team selectAll() {
        log.info("查询团队介绍");
        try {
            return teamMapper.selectAll();
        } catch (Exception e) {
            log.error("查询团队介绍时发生异常", e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void updateTeam(Team team) {
        log.info("更新团队信息, 新团队信息：{}", team);
        try {
            teamMapper.updateTeam(team);
        } catch (Exception e) {
            log.error("更新团队信息时发生异常，团队信息：{}", team, e);
            throw new DatabaseOperationException();
        }
    }

}
