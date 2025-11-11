package com.web.api.controller;

import com.web.api.pojo.Graduate;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.service.GraduatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 毕业生
 */
@Slf4j
@RestController
@RequestMapping({"/graduates", "/graduate"})
public class GraduateController {

    @Autowired
    private GraduatesService graduatesService;

    /**
     * 分页查询毕业生信息
     *
     * @param pageQueryDTO 分页查询参数
     * @return 毕业生分页结果
     */
    @GetMapping("/list")
    public Result page(PageQueryDTO pageQueryDTO) {
        log.info("分页查询毕业生信息：{}", pageQueryDTO);
        PageResult pageResult = graduatesService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 保存毕业生信息
     *
     * @param graduate 毕业生信息
     * @return 操作结果
     */
    @PostMapping
    public Result save(@RequestBody Graduate graduate) {
        log.info("保存毕业生信息：{}", graduate);
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
    public Result updateById(@RequestBody Graduate graduate) {
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
        List<Integer> ids = List.of(id);
        graduatesService.deleteBatchByIds(ids);
        return Result.success();
    }
}
