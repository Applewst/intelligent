package com.web.api.controller;

import com.web.api.service.impl.ResearchDirectionServiceImpl;
import com.web.api.service.impl.ScientificDynamicServiceImpl;
import com.web.api.service.impl.TeacherServiceImpl;
import com.web.api.service.impl.TeamDynamicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.api.pojo.Result;

/**
 * 首页内容
 */
@Slf4j
@RestController
public class HomePageController {

    @Autowired
    private TeamDynamicServiceImpl teamDynamicServiceImpl;

    @Autowired
    private ScientificDynamicServiceImpl scientificDynamicServiceImpl;

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    @Autowired
    private ResearchDirectionServiceImpl researchDirectionServiceImpl;

    /**
     * 最新动态
     */
    @GetMapping("/news")
    public Result news() {
        //返回最新4条动态
        return Result.success(scientificDynamicServiceImpl.NewDynamics(4));
    }

    /**
     * 研究方向
     */
    @GetMapping("/projects")
    public Result getProject() {
        return Result.success(researchDirectionServiceImpl.getAllDirection());
    }

    /**
     *
     *教师队伍
     */
    @GetMapping("/teachers")
    public Result getTeacher(){
        return Result.success(teacherServiceImpl.getAllTeachers());
    }

    /**
     * 照片墙
     */
    @GetMapping("/photos")
    public Result getPhoto(){
        return Result.success(teamDynamicServiceImpl.getAllImgs(6));
    }

}



















