package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.impl.ScientificDynamicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 科研动态
 */
@RestController
public class ScientificDynamicController {

    @Autowired
    private ScientificDynamicServiceImpl scientificDynamicServiceImpl;

    @GetMapping("/news/all")
    public Result getAllScientificDynamics() {
        return Result.success(scientificDynamicServiceImpl.getAllDynamics());
    }
}
