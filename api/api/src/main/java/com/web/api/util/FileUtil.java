package com.web.api.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileUtil {

    // 注入资源目录配置
    private static String RESOURCE_DIR;

    @Value("${file.resource-dir}")
    public void setResourceDir(String resourceDir) {
        RESOURCE_DIR = resourceDir;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "";
        }

        int lastIndex = fileName.lastIndexOf('.');
        // 点不存在或在最后一位，说明没有扩展名
        if (lastIndex <= 0 || lastIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastIndex + 1);
    }

    /**
     * 生成新的文件名
     *
     * @param fileName 原始文件名
     * @return 新文件名
     */
    public static String getNewName(String fileName) {
        // 生成新的文件名，使用UUID避免重名
        String extension = getExtension(fileName);
        // 拼接新的文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + extension;
    }


    /**
     * 上传文件
     *
     * @param file MultipartFile对象
     * @return 保存路径
     * @throws IOException 文件上传异常
     */
    public static String uploadFile(MultipartFile file) throws java.io.IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("上传失败：文件为空");
        }

        // 创建日期子目录，如 20251111
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File dir = new File(RESOURCE_DIR + dateDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("无法创建上传目录：" + dir.getAbsolutePath());
        }
        // 新文件名
        String newName = getNewName(file.getOriginalFilename());
        // 保存路径
        String savePath = dir.getAbsolutePath() + File.separator + newName;
        // 保存文件
        file.transferTo(new File(savePath));

        // 返回保存路径
        return savePath;
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return ResponseEntity包含文件流
     * @throws IOException 文件读取异常
     */
    public static ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在：" + filePath);
        }

        String fileName = file.getName();

        // 自动探测 Content-Type
        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) {
            // 探测不到就给个默认值
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // 构建响应
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok()
                .contentLength(file.length());
        if (contentType.startsWith("image/")) {
            // 图片：直接预览，不加 attachment
            builder.contentType(MediaType.parseMediaType(contentType));
        } else {
            // 其他文件：沿用原来的下载逻辑
            builder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM);
        }

        return builder.body(resource);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        // 判断文件是否存在或者不是文件
        if (!file.exists() || !file.isFile()) {
            return false;
        }

        // 尝试删除文件
        try {
            return file.delete();
        } catch (SecurityException e) {
            return false;
        }
    }

}
