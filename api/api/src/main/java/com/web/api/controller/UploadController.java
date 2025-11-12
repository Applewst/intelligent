package com.web.api.controller;

import com.web.api.pojo.Result;
import com.web.api.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @PostMapping("/file")
    public Result uploadFile(MultipartFile file) {
        log.info("接收到文件上传请求: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        try {
            String filePath = FileUtil.uploadFile(file);
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}
