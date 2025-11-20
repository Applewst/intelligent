package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.TeamDynamic;
import com.web.api.service.impl.TeamDynamicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 团队动态
 */
@Slf4j
@RestController
public class TeamDynamicController {

    @Autowired
    private TeamDynamicServiceImpl teamDynamicServiceImpl;

    @GetMapping("/events")
    public Result getAllTeamDynamics() {
        return Result.success(teamDynamicServiceImpl.getAllDynamics());
    }
    /**
     * 获取科研动态列表
     */
    @GetMapping("/event/list")
    public Result getAllDynamics(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam String title)
    {
        log.info("分页查询默认值pageNum:{},pageSize:{},title:{}",pageNum,pageSize,title );
        PageResult pageResult =teamDynamicServiceImpl.pageQuery(pageNum,pageSize,title);
        return Result.success(pageResult);
    }

    /**
     * 新增科研动态
     */
    @PostMapping("/events")
    public Result saveScientificDynamic(@RequestBody TeamDynamic teamDynamic) {
        log.info("新增团队动态 teamDynamic:{},title:{}",teamDynamic,teamDynamic.getTitle());
        teamDynamicServiceImpl.saveDynamics(teamDynamic);
        return Result.success();
    }

    /**
     * 删除科研动态
     */
    @DeleteMapping("/events")
    public Result deleteScientificDynamic(@RequestParam("id") int id) {
        log.info("删除团队动态 id:{}",id);
        teamDynamicServiceImpl.deleteDynamics(id);
        return Result.success();
    }

    /**
     * 修改科研动态
     */
    @PutMapping("/event")
    public Result updateScientificDynamic(@RequestBody TeamDynamic teamDynamic) {
        log.info("修改团队动态");
        teamDynamicServiceImpl.updateDynamics(teamDynamic);
        return Result.success();
    }
}
