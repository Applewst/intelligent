package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在读学生
 */
@RestController
public class StudentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @GetMapping("/students")
    public Result getAllStudents() {
        return Result.success(studentServiceImpl.getAllStudents());
    }
}
