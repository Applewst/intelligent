package com.web.api.controller;

import com.web.api.pojo.Resource;
import com.web.api.pojo.Result;
import com.web.api.service.ResourceService;
import com.web.api.util.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping
public class CommonController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 文件上传
     *
     * @param file 上传的文件
     * @return Result
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) throws Exception {
        try {
            // 指定oss保存文件路径 intelligent-resources/2025/11/4/ intelligent-resources/2025/11/5/
            String objectName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/";
            // 上传文件并返回新文件名称
            FileInfo fileInfo = fileStorageService.of(file)
                    .setPath(objectName)
                    .upload();
            return Result.success(fileInfo.getUrl());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param id 资源ID
     * @return 文件流
     */
    @GetMapping("/resources/{id}/download")
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
            return FileUtils.downloadFile(fileStorageService, filePath);
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
    @DeleteMapping("resources/{id}")
    public Result deleteFile(@PathVariable String id) {
        log.info("删除文件，id={}", id);
        // 1. 根据ID查找资源
        Resource resource = resourceService.getResourceById(id);
        // 2. 删除文件
        if (FileUtils.deleteFile(fileStorageService, resource.getUrl())) {
            resourceService.deleteResourceById(id);
            return Result.success();
        } else {
            return Result.error("文件删除失败");
        }
    }
}
