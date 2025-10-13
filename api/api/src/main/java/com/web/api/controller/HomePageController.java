package com.web.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.api.pojo.Result;

/**
 * 首页内容
 */
@Slf4j
@RestController
public class HomePageController {

    /**
     * 教师队伍
     */
    @PostMapping("/teacher")
    public Result teacher() {
        return new Result();
    }

    /**
     * 照片墙
     */
    @PostMapping("/photos")
    public Result photos() {
        return new Result();
    }

    /**
     * 研究方向
     */
    @PostMapping("/project")
    public Result project() {
        return new Result();
    }

    /**
     * 最新动态
     */
    @PostMapping("/news")
    public Result news() {
        return new Result();
    }
}

















