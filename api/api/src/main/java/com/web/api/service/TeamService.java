package com.web.api.service;

import com.web.api.pojo.Team;

import java.util.List;

/**
 * 团队介绍服务接口
 * @author Askr-Yggdrasill
 */
public interface TeamService {

    /**
     * 查询团队介绍
     * @return 团队介绍
     */
    public Team selectAll();

    /**
     * 更新团队信息
     * @param team 团队信息
     */
    public void updateTeam(Team team);
}
