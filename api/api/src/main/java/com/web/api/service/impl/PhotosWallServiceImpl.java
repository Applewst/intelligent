package com.web.api.service.impl;

import com.web.api.mapper.PhotosWallMapper;
import com.web.api.pojo.Result;
import com.web.api.service.PhotosWallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotosWallServiceImpl implements PhotosWallService {

    @Autowired
    private PhotosWallMapper photosWallMapper;

    @Override
    public Result getPhotosWall() {
        return Result.success(photosWallMapper.getPhotos());
    }
}
