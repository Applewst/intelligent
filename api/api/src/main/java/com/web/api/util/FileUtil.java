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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileUtil {

    // 注入资源目录配置
    private static String RESOURCE_DIR;
    // 注入访问域名配置
    private static String ACCESS_DOMAIN;
    // 注入访问前缀配置
    private static String ACCESS_PREFIX;

    @Value("${file.resource-dir}")
    public void setResourceDir(String resourceDir) {
        RESOURCE_DIR = resourceDir;
    }

    @Value("${file.access-domain}")
    public void setAccessDomain(String accessDomain) {
        ACCESS_DOMAIN = accessDomain;
    }

    @Value("${file.access-prefix}")
    public void setAccessPrefix(String accessPrefix) {
        ACCESS_PREFIX = accessPrefix;
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
     * @param file 上传的文件
     * @return 文件访问路径
     * @throws IOException 文件保存异常
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

        // 构造可访问 URL，例如 http://localhost:8080/files/20251120/xxx.png
        String urlPath = dateDir + File.separator + newName;
        return ACCESS_DOMAIN + ACCESS_PREFIX + urlPath;
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 响应实体
     * @throws IOException 文件读取异常
     */
    public static ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        // 1. 校验并去掉域名+前缀
        String prefix = ACCESS_DOMAIN + ACCESS_PREFIX; // 如 http://localhost:8080/files/
        if (!filePath.startsWith(prefix)) {
            throw new IllegalArgumentException("无效的文件地址");
        }
        // 剩下的相对路径：20251120/xxx.png
        String relativePath = filePath.substring(prefix.length());

        // 2. 还原成本地磁盘路径
        filePath = RESOURCE_DIR + relativePath.replace("/", File.separator);

        // 3. 读取文件
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在：" + filePath);
        }
        String fileName = file.getName();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // 4. 构建响应
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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
