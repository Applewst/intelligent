package com.web.api.controller;

import com.web.api.service.impl.DynamicServiceImpl;
import com.web.api.service.impl.SearchDirectionServiceImpl;
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
    private DynamicServiceImpl dynamicServiceImpl;

    @Autowired
    private SearchDirectionServiceImpl searchDirectionServiceImpl;

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
}

















