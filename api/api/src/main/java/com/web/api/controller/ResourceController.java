package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;
import com.web.api.pojo.Result;
import com.web.api.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
@Slf4j
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 分页查询资源列表
     *
     * @param pageQueryDTO 分页查询参数
     * @param keyword      关键词（可选）
     * @return 资源列表
     */
    @GetMapping
    public Result page(PageQueryDTO pageQueryDTO, String keyword) {
        log.info("分页查询资源列表，pageQueryDTO={}, keyword={}", pageQueryDTO, keyword);
        if (keyword != null && !keyword.trim().isEmpty()) {
            pageQueryDTO.setName(keyword.trim());
        }
        PageResult pageResult = resourceService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 保存资源信息
     *
     * @param resource 资源信息
     * @return 操作结果
     */
    @PostMapping
    public Result saveResource(@RequestBody Resource resource) {
        log.info("保存资源信息，resource={}", resource);
        resourceService.saveResource(resource);
        return Result.success();
    }

    /**
     * 更新资源信息
     *
     * @param resource 资源信息
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result updateResource(@RequestBody Resource resource) {
        log.info("更新资源信息，resource={}", resource);
        resourceService.updateResource(resource);
        return Result.success();
    }
}
