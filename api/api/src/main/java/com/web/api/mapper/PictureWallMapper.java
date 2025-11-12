package com.web.api.mapper;

import com.web.api.pojo.PictureWall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片墙数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface PictureWallMapper {

    List<PictureWall> getPictureWall(String name);

    void addPictureWall(PictureWall pictureWall);

    void updatePictureWall(PictureWall pictureWall);

    void deletePictureWall( Integer id);
}
