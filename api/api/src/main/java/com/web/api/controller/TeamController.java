package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.pojo.Team;
import com.web.api.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 团队介绍控制器
 * @author Askr-Yggdrasill
 */
@Slf4j
@RestController
@RequestMapping("/introduce")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 查询团队介绍
     * @return 团队介绍
     */
    @GetMapping("")
    public Result selectAll() {
        log.info("查询团队介绍");
        Team team = teamService.selectAll();
        return Result.success(team);
    }

    /**
     * 更新团队信息
     * @param team 团队信息
     * @return 操作结果
     */
    @PutMapping("")
    public Result updateTeam(@RequestBody Team team) {
        log.info("更新团队信息");
        team.setId(1);
        teamService.updateTeam(team);
        return Result.success();
    }
}
