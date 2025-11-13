package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PictureWall;
import com.web.api.pojo.Result;
import com.web.api.service.PictureWallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 照片墙控制器
 * @author Askr-Yggdrasill
 */
@RestController
@RequestMapping("/team/shoots")
public class PictureWallController {

    @Autowired
    private PictureWallService pictureWallService;

    /**
     * 获取照片墙列表（分页）
     */
    @GetMapping("")
    public Result getPictureWallList(PageQueryDTO pageQueryDTO,
                                     @RequestParam String title) {
        pageQueryDTO.setName(title);
        return Result.success(pictureWallService.getPictureWallList(pageQueryDTO));
    }

    /**
     * 添加照片墙
     */
    @PostMapping("")
    public Result addPictureWall(@RequestBody PictureWall pictureWall) {
        pictureWallService.addPictureWall(pictureWall);
        return Result.success();
    }

    /**
     * 更新照片墙
     */
    @PutMapping("")
    public Result updatePictureWall(@RequestBody PictureWall pictureWall) {
        pictureWallService.updatePictureWall(pictureWall);
        return Result.success();
    }

    /**
     * 删除照片墙
     */
    @DeleteMapping("")
    public Result deletePictureWall(Integer id) {
        pictureWallService.deletePictureWall(id);
        return Result.success();
    }
}
