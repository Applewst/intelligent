package com.web.api.util.file;

import java.io.File;

/**
 * 文件类型工具类
 *
 * @author kkstar
 */
public class FileTypeUtils {

    /**
     * 获取文件类型
     * 例如: file.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file) {
        if (null == file) {
            return "";
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * 例如: file.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0 || separatorIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }
}
