package com.web.api.util.file;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class FileUtils {

    /**
     * 下载文件（不依赖 FileRecorder，只用 URL）
     *
     * @param fileStorageService 文件存储服务
     * @param fileUrl            文件 URL（minio/x-file-storage 返回的 url）
     */
    public static ResponseEntity<InputStreamResource> downloadFile(
            FileStorageService fileStorageService,
            String fileUrl) throws IOException {

        // 1. URL -> FileInfo
        FileInfo fileInfo = FileInfoUtil.buildFileInfoFromUrl(fileUrl);

        // 2. 判断文件是否存在
        boolean exists = fileStorageService.exists(fileInfo);
        if (!exists) {
            throw new FileNotFoundException("文件不存在：" + fileUrl);
        }

        // 3. 下载
        byte[] bytes = fileStorageService.download(fileInfo).bytes();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

        // 4. 文件名带后缀
        String filename = fileInfo.getFilename();

        // 5. 构造 HTTP Header
        HttpHeaders headers = new HttpHeaders();
        // URL 编码 UTF-8，给 filename*
        String encoded = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
        String contentDisposition =
                "attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + encoded;
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        // 6. 返回响应
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    /**
     * 删除文件（基于 x-file-storage，不依赖 FileRecorder）
     *
     * @param fileStorageService 文件存储服务
     * @param fileUrl            文件 URL（上传返回的 url）
     * @return 是否删除成功
     */
    public static boolean deleteFile(FileStorageService fileStorageService, String fileUrl) {

        if (fileUrl == null || fileUrl.isEmpty()) {
            return false;
        }

        try {
            // 尝试把 URL 转成 FileInfo
            FileInfo fileInfo;
            try {
                fileInfo = FileInfoUtil.buildFileInfoFromUrl(fileUrl);
            } catch (FileNotFoundException e) {
                // 说明 URL 不属于我们平台，例如外链，直接当删除成功
                return true;
            }

            // 1. 先检查是否存在
            if (!fileStorageService.exists(fileInfo)) {
                return true; // 不存在也算成功
            }

            // 2. 正常删除
            fileStorageService.delete(fileInfo);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
