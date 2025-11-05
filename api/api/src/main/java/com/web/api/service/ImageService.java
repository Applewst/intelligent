package com.web.api.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片服务接口
 */
public interface ImageService {

    public void saveImage(MultipartFile file, String newName);

    public void deleteImage(String imageUrl);
}
