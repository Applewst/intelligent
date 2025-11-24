package com.web.api.util.file;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class FileUtils {

    /**
     * 下载文件
     *
     * @param fileStorageService 文件存储服务
     * @param fileUrl            文件 URL
     * @return ResponseEntity 包含文件流
     * @throws IOException 如果文件读取失败
     */
    public static ResponseEntity<InputStreamResource> downloadFile(FileStorageService fileStorageService
            , String fileUrl) throws IOException {
        // 1. 从 url 查询文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(fileUrl);
        if (fileInfo == null) {
            throw new FileNotFoundException("文件不存在：" + fileUrl);
        }

        // 2. 用 bytes() 直接拿到文件内容
        byte[] bytes = fileStorageService.download(fileInfo).bytes();

        // 3. 包一层 InputStreamResource
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

        // 4. 组装文件名
        String filename = Optional.ofNullable(fileInfo.getOriginalFilename())
                .orElse(Optional.ofNullable(fileInfo.getFilename()).orElse("file"));

        // 5. 统一下载（二进制流）
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(filename, StandardCharsets.UTF_8)
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)                 // 用实际长度
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // 强制下载
                .body(resource);
    }

    /**
     * 删除文件（基于 x-file-storage）
     *
     * @param filePath 文件路径 / URL（上传返回的 url）
     * @return 是否删除成功
     */
    public static boolean deleteFile(FileStorageService fileStorageService, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        // 1. 通过 URL 查出 FileInfo
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(filePath);
        if (fileInfo == null) {
            // 记录不存在，认为已经被删了
            return false;
        }

        // 2. 调用 x-file-storage 删除
        try {
            fileStorageService.delete(fileInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
