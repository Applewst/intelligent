package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.impl.TeamDynamicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队动态
 */
@RestController
public class TeamDynamicController {

    @Autowired
    private TeamDynamicServiceImpl teamDynamicServiceImpl;
}
