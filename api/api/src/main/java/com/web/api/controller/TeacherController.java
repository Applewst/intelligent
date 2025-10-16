package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教师
 */
@RestController
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    @GetMapping("/teacher/{id}")
    public Result getAllTeachers(@PathVariable int id) {
        return Result.success(teacherServiceImpl.getTeacherById(id));
    }
}
