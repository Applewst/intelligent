package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;
import com.web.api.pojo.Result;
import com.web.api.service.ResourceService;
import com.web.api.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    /**
     * 下载文件
     *
     * @param id 资源ID
     * @return 文件流
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String id) {
        log.info("下载文件，id={}", id);
        // 1. 根据ID查询数据库中的资源记录
        Resource resource = resourceService.getResourceById(id);
        if (resource == null) {
            log.warn("资源未找到，id={}", id);
            return ResponseEntity.notFound().build();
        }
        // 2. 获取文件路径
        String filePath = resource.getUrl();
        // 3. 更新下载次数
        resource.setDownloads(resource.getDownloads() + 1);
        resourceService.updateResource(resource);

        try {
            // 4. 调用工具类下载方法
            return FileUtil.downloadFile(filePath);
        } catch (FileNotFoundException e) {
            log.error("文件未找到: {}", filePath, e);
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            log.error("文件读取错误: {}", filePath, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * 删除文件
     *
     * @param id 资源ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result deleteFile(@PathVariable String id) {
        log.info("删除文件，id={}", id);
        // 1. 根据ID查找资源
        Resource resource = resourceService.getResourceById(id);
        // 2. 删除文件
        if (FileUtil.deleteFile(resource.getUrl())) {
            resourceService.deleteResourceById(id);
            return Result.success();
        } else {
            return Result.error("文件删除失败");
        }
    }
}
