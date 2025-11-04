package com.web.api.controller;

import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.Teacher;
import com.web.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询教师
     *
     * @param pageNum  当前页码
     * @param pageSize 分页大小
     * @return 分页结果
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("教师分页查询，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageResult pageResult = teacherService.page(pageNum, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询教师
     *
     * @param id 教师ID
     * @return 教师信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询教师，ID：{}", id);
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }

    /**
     * 保存教师信息
     *
     * @param teacher 教师信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(Teacher teacher) {
        log.info("保存教师信息：{}", teacher);
        teacherService.save(teacher);
        return Result.success();
    }

    /**
     * 根据ID更新教师信息
     *
     * @param teacher 教师信息
     * @return 操作结果
     */
    @PutMapping
    public Result updateById(Teacher teacher) {
        log.info("更新教师信息：{}", teacher);
        teacherService.updateById(teacher);
        return Result.success();
    }

    /**
     * 根据ID删除教师
     *
     * @param id 教师ID
     * @return 操作结果
     */
    @DeleteMapping
    public Result deleteById(Integer id) {
        log.info("根据ID删除教师，ID：{}", id);
        List<Integer> ids = List.of(id);
        teacherService.deleteBatchByIds(ids);
        return Result.success();
    }
}
