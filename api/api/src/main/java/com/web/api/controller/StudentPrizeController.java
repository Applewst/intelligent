package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Result;
import com.web.api.pojo.StudentPrize;
import com.web.api.service.StudentPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生获奖信息控制器
 * @author Askr-Yggdrasill
 */
@Slf4j
@RestController
@RequestMapping("/search/awards")
public class StudentPrizeController {

    @Autowired
    private StudentPrizeService studentPrizeService;

    /**
     * 获取所有学生获奖信息（分页）
     * @param pageQueryDTO 分页查询参数
     *                     author 作者姓名
     */
    @GetMapping("/list")
    public Result getStudentPrizeList(PageQueryDTO pageQueryDTO, @RequestParam String author) {
        pageQueryDTO.setName(author);
        return Result.success(studentPrizeService.getAllStudentPrizes(pageQueryDTO));
    }

    /**
     * 添加学生获奖信息
     * @param studentPrize 学生获奖信息
     */
    @PostMapping("")
    public Result addStudentPrize(@RequestBody StudentPrize studentPrize) {
        studentPrizeService.addStudentPrize(studentPrize);
        return Result.success();
    }

    /**
     * 修改学生获奖信息
     * @param studentPrize 学生获奖信息
     */
    @PutMapping("")
    public Result modifyStudentPrizeById(@RequestBody StudentPrize studentPrize) {
        studentPrizeService.motifyStudentPrizeById(studentPrize);
        return Result.success();
    }

    /**
     * 删除学生获奖信息
     * @param id 学生获奖信息ID
     */
    @DeleteMapping("")
    public Result deleteStudentPrizeById(Integer id) {
        studentPrizeService.deleteStudentPrizeById(id);
        return Result.success();
    }
}
