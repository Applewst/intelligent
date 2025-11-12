package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.PictureWall;

/**
 * 图片墙服务接口
 * @author Askr-Yggdrasill
 */
public interface PictureWallService {
    /**
     * 获取图片墙列表
     * @param pageQueryDTO 分页查询参数
     * @return 图片墙分页结果
     */
    PageResult getPictureWallList(PageQueryDTO pageQueryDTO);

    /**
     * 添加图片墙
     * @param pictureWall 图片墙信息
     */
    void addPictureWall(PictureWall pictureWall);

    /**
     * 更新图片墙
     * @param pictureWall 图片墙信息
     */
    void updatePictureWall(PictureWall pictureWall);

    /**
     * 删除图片墙
     * @param id 图片墙ID
     */
    void deletePictureWall(Integer id);
}
