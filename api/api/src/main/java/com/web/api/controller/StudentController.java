package com.web.api.controller;

import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.Student;
import com.web.api.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 在读学生
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询在读学生信息
     *
     * @param pageNum  当前页数
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询在读学生信息：pageNum={}, pageSize={}", pageNum, pageSize);
        PageResult pageResult = studentService.pageQuery(pageNum, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 新增在读学生信息
     *
     * @param student 在读学生信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(Student student) {
        log.info("新增在读学生信息：{}", student);
        studentService.save(student);
        return Result.success();
    }

    /**
     * 修改在读学生信息
     *
     * @param student 在读学生信息
     * @return 操作结果
     */
    @PutMapping
    public Result updateById(Student student) {
        log.info("修改在读学生信息：{}", student);
        studentService.updateById(student);
        return Result.success();
    }

    /**
     * 根据ID删除在读学生信息
     *
     * @param id 在读学生ID
     * @return 操作结果
     */
    @DeleteMapping
    public Result deleteById(Integer id) {
        log.info("根据ID删除在读学生信息：id={}", id);
        List<Integer> ids = List.of(id);
        studentService.deleteBatchByIds(ids);
        return Result.success();
    }
}
