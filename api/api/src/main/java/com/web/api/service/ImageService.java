package com.web.api.service;

/**
 * 图片服务接口
 */
public interface ImageService {

    /**
     * 保存图片
     * @param url 图片URL
     * @param newName 新文件名
     */
    void saveImage(String url, String newName);

    /**
     * 删除图片
     * @param imageUrl 图片URL
     */
    void deleteImage(String imageUrl);
}
