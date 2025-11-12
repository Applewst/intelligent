package com.web.api.controller;

import com.web.api.mapper.ScientificDynamicMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.service.DynamicService;
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
    public Result getAllDynamics(@RequestParam(defaultValue =" 1") int pageNum,
                                 @RequestParam(defaultValue =" 10")int pageSize,
                                 @RequestParam(name ="name") String title)
    {
    log.info("分页查询默认值pageNum:{},pageSize:{},title:{}", pageNum, pageSize, title);
        PageResult pageResult =scientificDynamicServiceImpl.pageQuery(pageNum, pageSize,title);
    return Result.success(pageResult);
    }

    /**
     * 新增科研动态
     */
    @PostMapping("/news")
    public Result addScientificDynamic(@RequestParam("title") String title,
                                       @RequestParam("image") String image ,
                                       @RequestParam("detail") String detail) {
        log.info("新增科研动态 image :{},title :{},detail :{}", image, title, detail);
        scientificDynamicServiceImpl.saveDynamics(image, title, detail);
        return Result.success();
    }

    /**
     * 删除科研动态
     */
    @DeleteMapping("/news/all")
    public Result deleteScientificDynamic(@RequestParam("id") int id) {
        log.info("删除科研动态 id:{}",id);
        scientificDynamicServiceImpl.deleteDynamics(id);
        return Result.success();
    }

    /**
     * 修改科研动态
     */
    @PutMapping("/news/Pall")
    public Result updateScientificDynamic(@RequestParam int id,
                                          @RequestParam String title,
                                          @RequestParam String image,
                                          @RequestParam String detail,
                                          @RequestParam String time) {
        log.info("修改科研动态");
        scientificDynamicServiceImpl.updateDynamics(id,title,image,detail,time);
        return Result.success();
    }
}
