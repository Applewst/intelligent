package com.web.api.service.impl;

import com.web.api.service.ImageService;
import com.web.api.util.FileUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    // 从配置文件读取上传根目录，例如：uploads
    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            // 使用应用工作目录下的 uploads/images 作为根目录（可外部配置）
            this.rootLocation = Paths.get(uploadDir, "images").toAbsolutePath().normalize();
            Files.createDirectories(this.rootLocation);
            log.info("上传根目录初始化完成: {}", this.rootLocation);
        } catch (IOException e) {
            log.error("无法初始化上传目录", e);
            throw new RuntimeException("上传目录初始化失败", e);
        }
    }

    @Override
    public void saveImage(MultipartFile file, String newName) {
        if (file == null || file.isEmpty()) {
            log.warn("上传的文件为空");
            throw new IllegalArgumentException("文件为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty() || originalFilename.length() > 40) {
            throw new IllegalArgumentException("文件名过长或非法");
        }

        String extension = FileUtil.getExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            log.warn("不支持的文件类型: {}", extension);
            throw new IllegalArgumentException("不支持的文件类型: " + extension);
        }

        try {
            // 安全地解析目标路径
            Path destinationFile = this.rootLocation.resolve(newName).normalize().toAbsolutePath();
            if (!destinationFile.startsWith(this.rootLocation)) {
                throw new RuntimeException("无法将文件存储在上传目录之外");
            }
            Files.copy(file.getInputStream(), destinationFile);
            log.info("文件已保存: {}", destinationFile);
        } catch (IOException e) {
            log.error("文件保存失败", e);
            throw new RuntimeException("文件保存失败", e);
        }
    }

    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            log.warn("图片URL为空，无法删除");
            return;
        }

        String filename = Paths.get(imageUrl).getFileName().toString();

        // 防止路径穿越
        if (!filename.equals(imageUrl)) {
            log.warn("非法图片路径: {}", imageUrl);
            return;
        }

        Path filePath = this.rootLocation.resolve(filename).normalize().toAbsolutePath();
        if (!filePath.startsWith(this.rootLocation)) {
            log.warn("拒绝删除目录外文件: {}", filePath);
            return;
        }

        try {
            if (Files.deleteIfExists(filePath)) {
                log.info("已删除图片文件: {}", filePath);
            } else {
                log.warn("图片文件不存在，未删除: {}", filePath);
            }
        } catch (IOException e) {
            log.error("删除图片文件时发生错误: {}", filePath, e);
        }
    }
}