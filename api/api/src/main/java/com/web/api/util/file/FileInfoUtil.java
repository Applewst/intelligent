package com.web.api.util.file;

import org.dromara.x.file.storage.core.FileInfo;

import java.io.FileNotFoundException;

public class FileInfoUtil {

    // OSS 平台标识
    private static final String PLATFORM = "minio-1";
    // OSS 基础路径
    private static final String BASE_PATH = "intelligent-resources/";

    public static FileInfo buildFileInfoFromUrl(String fileUrl) throws FileNotFoundException {

        int baseIndex = fileUrl.indexOf(BASE_PATH);
        if (baseIndex < 0) {
            throw new FileNotFoundException("URL 中不包含 basePath：" + BASE_PATH + "，url=" + fileUrl);
        }

        // 从 basePath 后面开始的部分：2025/11/24/xxxx.png
        String key = fileUrl.substring(baseIndex + BASE_PATH.length());

        int idx = key.lastIndexOf('/');
        String path = idx != -1 ? key.substring(0, idx + 1) : "";
        String filename = idx != -1 ? key.substring(idx + 1) : key;

        FileInfo fileInfo = new FileInfo();
        fileInfo.setPlatform(PLATFORM);
        fileInfo.setBasePath(BASE_PATH);
        fileInfo.setPath(path);
        fileInfo.setFilename(filename);
        fileInfo.setOriginalFilename(filename);
        fileInfo.setUrl(fileUrl); // 可有可无

        return fileInfo;
    }
}
