package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.impl.GraduatesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 毕业生
 */
@RestController
public class Graduate {

    @Autowired
    private GraduatesServiceImpl graduatesServiceImpl;

    @GetMapping("/graduates")
    public Result getAllGraduates() {
        return Result.success(graduatesServiceImpl.getAllGraduates());
    }
}
