package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Result;
import com.web.api.pojo.StudentDevelopment;
import com.web.api.service.StudentDevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生发展控制器
 * @author Askr-Yggdrasill
 */
@RestController
@RequestMapping("/team/developments")
public class StudentDevelopmentController {

    @Autowired
    private StudentDevelopmentService studentDevelopmentService;

    /**
     * 获取所有学生成长信息（分页）
     */
    @GetMapping("")
    public Result getStudentDevelopmentList(PageQueryDTO pageQueryDTO) {
        return Result.success(studentDevelopmentService.getAllStudentDevelopment(pageQueryDTO));
    }

    /**
     * 添加学生成长信息
     */
    @PostMapping("")
    public Result addStudentDevelopment(@RequestBody StudentDevelopment studentDevelopment) {
        studentDevelopmentService.addStudentDevelopment(studentDevelopment);
        return Result.success();
    }

    /**
     * 修改学生成长信息
     */
    @PutMapping("")
    public Result modifyStudentDevelopmentById(@RequestBody StudentDevelopment studentDevelopment) {
        studentDevelopmentService.motifyStudentDevelopmentById(studentDevelopment);
        return Result.success();
    }

    /**
     * 删除学生成长信息
     */
    @DeleteMapping("")
    public Result deleteStudentDevelopmentById( Integer id) {
        studentDevelopmentService.deleteStudentDevelopmentById(id);
        return Result.success();
    }
}
