package com.web.api.controller;

import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.service.impl.ScientificDynamicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 科研动态
 */
@Slf4j
@RestController
public class ScientificDynamicController {

    @Autowired
    private ScientificDynamicServiceImpl scientificDynamicServiceImpl;

    /**
     * 获取所有科研动态
     */
    @GetMapping("/news/all")
    public Result getAllScientificDynamics() {
        return Result.success(scientificDynamicServiceImpl.getAllDynamics());
    }

    /**
     * 获取科研动态列表
     */
    @GetMapping("/new/list")
    public Result getAllDynamics(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam String title) {
        log.info("分页查询默认值pageNum:{},pageSize:{},title:{}", pageNum, pageSize, title);
        PageResult pageResult = scientificDynamicServiceImpl.pageQuery(pageNum, pageSize, title);
        return Result.success(pageResult);
    }

    /**
     * 新增科研动态
     */
    @PostMapping("/news")
    public Result saveScientificDynamic(@RequestBody ScientificDynamic scientificDynamic) {
        log.info("新增科研动态 scientificDynamic:{}", scientificDynamic);
        scientificDynamicServiceImpl.saveDynamics(scientificDynamic);
        return Result.success();
    }

    /**
     * 删除科研动态
     */
    @DeleteMapping("/news/all")
    public Result deleteScientificDynamic(@RequestParam("id") Integer id) {
        log.info("删除科研动态 id:{}", id);
        if (id == null) {
            log.error("id为空,删除错误");
            return Result.error("id不能为空");
        }
        scientificDynamicServiceImpl.deleteDynamics(id);
        return Result.success();
    }

    /**
     * 修改科研动态
     */
    @PutMapping("/news/Pall")
    public Result updateScientificDynamic(@RequestBody ScientificDynamic scientificDynamic) {
        log.info("修改科研动态");
        scientificDynamicServiceImpl.updateDynamics(scientificDynamic);
        return Result.success();
    }
}
