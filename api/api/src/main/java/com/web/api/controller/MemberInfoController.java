package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.ResearchDirectionDevelopmentService;
import com.web.api.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberInfoController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Autowired
    private GraduatesServiceImpl graduatesServiceImpl;


    @Autowired
    private TeacherInfoServiceImpl teacherInfoServiceImpl;

    @Autowired
    private ResearchDirectionDevelopmentService researchDirectionDevelopmentServiceImpl;

    @Autowired
    private TeamEventServiceImpl teamEventServiceImpl;

    /**
     * 学生信息
     * @return
     */
    @GetMapping("/students")
    public Result getStudent() {return studentServiceImpl.getStudent();}

    /**
     * 毕业生信息
     * @return
     */
    @GetMapping("/graduates")
    public Result getGraduates(){
        return  graduatesServiceImpl.getGraduates();
    }

    /**
     * 教师信息
     * @param id
     * @return
     */
    @GetMapping("/teacher/{id}")
    public Result getTeacherInfo(@PathVariable  int id){
        return teacherInfoServiceImpl.getTeacherInfo(id);
    }

    /**
     * 科研动态
     * @return
     */
    @GetMapping("/news/all")
    public Result getResearchDirectionDevelopment() {return researchDirectionDevelopmentServiceImpl.searchDirectionDevelopment();}

    /**
     * 团队动态
     */
    @GetMapping("/events")
    public Result getEvents() {
        return teamEventServiceImpl.getTeamEvent();
    }

}
