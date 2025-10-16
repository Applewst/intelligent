package com.web.api.mapper;

import com.web.api.pojo.PhotosWall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface PhotosWallMapper {
    @Select("select * from photoswall limit 6")
    List<PhotosWall> getPhotos();
}
