package com.web.api.controller;


import com.web.api.mapper.TeacherMapper;
import com.web.api.service.TeacherService;
import com.web.api.service.impl.DynamicServiceImpl;
import com.web.api.service.impl.PhotosWallServiceImpl;
import com.web.api.service.impl.SearchDirectionServiceImpl;
import com.web.api.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
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
    private DynamicServiceImpl dynamicServiceImpl;

    @Autowired
    private SearchDirectionServiceImpl searchDirectionServiceImpl;

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    @Autowired
    private PhotosWallServiceImpl photosWallServiceImpl;

    /**
     * 最新动态
     */

    @GetMapping("/news")
    public Result news() {
        return dynamicServiceImpl.NewDynamic();
    }

    /**
     * 研究方向
     */

    @GetMapping("/projects")
    public Result getProject() {
        return searchDirectionServiceImpl.searchDirection();
    }

    /**
     *
     *教师队伍
     */

    @GetMapping("/teachers")
    public Result getTeacher(){return teacherServiceImpl.getTeacher(); }
    /**
     * 照片墙
     */

    @GetMapping("/photos")
    public Result getPhoto(){
        return photosWallServiceImpl.getPhotosWall();
    }
}



















