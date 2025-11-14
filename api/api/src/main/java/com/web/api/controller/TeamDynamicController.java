package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
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
    public Result getAllDynamics(PageQueryDTO pageQueryDTO)
    {
        log.info("分页查询默认值pageQueryDTO:{}", pageQueryDTO);
        PageResult pageResult =teamDynamicServiceImpl.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增科研动态
     */
    @PostMapping("/events")
    public Result addScientificDynamic(@RequestParam("title") String title,
                                       @RequestParam("image") String image ,
                                       @RequestParam("detail") String detail) {
        log.info("新增团队动态 image :{},title :{},detail :{}", image, title, detail);
        teamDynamicServiceImpl.saveDynamics(image, title, detail);
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
    public Result updateScientificDynamic(@RequestParam int id,
                                          @RequestParam String title,
                                          @RequestParam String image,
                                          @RequestParam String detail,
                                          @RequestParam String time) {
        log.info("修改团队动态");
        teamDynamicServiceImpl.updateDynamics(id,title,image,detail,time);
        return Result.success();
    }
}
