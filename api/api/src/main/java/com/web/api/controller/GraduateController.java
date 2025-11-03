package com.web.api.controller;

import com.web.api.pojo.Graduate;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.service.GraduatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 毕业生
 */
@Slf4j
@RestController
@RequestMapping("/graduates")
public class GraduateController {

    @Autowired
    private GraduatesService graduatesService;

    /**
     * 分页查询毕业生信息
     *
     * @param pageNum  分页页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @GetMapping
    public Result page(Integer pageNum, Integer pageSize) {
        log.info("分页查询毕业生信息：pageNum={}, pageSize={}", pageNum, pageSize);
        PageResult pageResult = graduatesService.pageQuery(pageNum, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 保存毕业生信息
     *
     * @param graduate 毕业生信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(Graduate graduate) {
        log.info("保存毕业生信息：{}", graduate);
        if (graduate.getGender() == null) {
            graduate.setGender(0);
        }
        if (graduate.getAvatar() == null || graduate.getAvatar().isEmpty()) {
            graduate.setAvatar(null);
        }
        graduatesService.save(graduate);
        return Result.success();
    }

    /**
     * 根据ID更新毕业生信息
     *
     * @param graduate 毕业生信息
     * @return 操作结果
     */
    @PutMapping
    public Result updateById(Graduate graduate) {
        log.info("根据ID更新毕业生信息：{}", graduate);
        graduatesService.updateById(graduate);
        return Result.success();
    }

    /**
     * 根据ID删除毕业生信息
     *
     * @param id 毕业生ID
     * @return 操作结果
     */
    @DeleteMapping
    public Result deleteById(Integer id) {
        log.info("根据ID删除毕业生信息：id={}", id);
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        graduatesService.deleteBatchByIds(ids);
        return Result.success();
    }
}
