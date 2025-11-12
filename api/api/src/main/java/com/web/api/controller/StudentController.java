package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
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
     * @param pageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    @GetMapping("/list")
    public Result page(PageQueryDTO pageQueryDTO) {
        log.info("分页查询在读学生信息：{}", pageQueryDTO);
        PageResult pageResult = studentService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增在读学生信息
     *
     * @param student 在读学生信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(@RequestBody Student student) {
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
    @PutMapping("/{id}")
    public Result updateById(@PathVariable Integer id, @RequestBody Student student) {
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
