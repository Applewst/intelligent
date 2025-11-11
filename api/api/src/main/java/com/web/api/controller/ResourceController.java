package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Resource;
import com.web.api.pojo.Result;
import com.web.api.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
     * 上传文件
     *
     * @param file 文件
     * @param name 文件名
     * @param type 文件类型
     * @param size 文件大小
     * @return 文件访问URL
     */
    public Result uploadFile(@RequestPart MultipartFile file,
                             @RequestPart String name,
                             @RequestPart String type,
                             @RequestPart String size) {
        log.info("上传文件，name={}, type={}, size={}", name, type, size);
        Resource resource = Resource.builder()
                .name(name)
                .type(type)
                .size(size)
                .updateTime(LocalDateTime.now())
                .build();
        String url = resourceService.uploadFile(file, resourceDTO);
        return Result.success(url);
    }

}
