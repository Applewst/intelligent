package com.web.api.util;

import java.util.UUID;

public class FileUtil {

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 文件扩展名(小写)
     */
    public static String getExtension(String fileName) {
        // 获取文件扩展名
        int lastDotIndex = fileName.lastIndexOf(".");
        // 当前点存在且不是最后一个字符时，返回扩展名
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        } else {
            //返回空字符串: xx.
            return "";
        }
    }

    /**
     * 获取文件名(不含扩展名)
     * @param fileName 文件名
     * @return 不含扩展名的文件名
     */
    public static String getFileName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return  fileName.substring(0, lastDotIndex);
    }

    /**
     * 生成新的文件名
     * @param fileName 原始文件名
     * @return 新文件名
     */
    public static String getNewName(String fileName) {
        // 获取文件扩展名
        String extension = getExtension(fileName);
        // 获取不含扩展名的文件名
        String fileNameWithoutExt = getFileName(fileName);
        // 生成新的文件名(UUID + 扩展名)
        return fileNameWithoutExt + "__" +  UUID.randomUUID() + "." + extension;
    }
}
