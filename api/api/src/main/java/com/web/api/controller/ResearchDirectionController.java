package com.web.api.controller;

import com.web.api.exception.NoIdException;
import com.web.api.pojo.ResearchDirection;
import com.web.api.pojo.Result;
import com.web.api.service.ImageService;
import com.web.api.service.ResearchDirectionService;
import com.web.api.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 研究方向
 */
@Slf4j
@RestController
@RequestMapping("/search/project")
public class ResearchDirectionController {

    @Autowired
    private ResearchDirectionService researchDirectionService;

    @Autowired
    private ImageService imageService;

    /**
     * 根据ID获取研究方向
     * @param id 研究方向ID
     * @return 研究方向
     */
    @GetMapping("/{id}")
    public Result getDirectionsById(@PathVariable Integer id) {
        if (id == null) {
            log.warn("未选择任何数据，ID为空");
            throw new NoIdException();
        }
        return Result.success(researchDirectionService.getDirectionById(id));
    }

    /**
     * 添加新的研究方向
     * @param title 研究方向标题
     *@param image 研究方向图片
     * @return 操作结果
     */
    @PostMapping("")
    public Result addDirection(@RequestParam("title") String title,
                               @RequestParam("image") MultipartFile image) {
        //封装研究方向对象
        ResearchDirection researchDirection = new ResearchDirection();
        researchDirection.setTitle(title);
        researchDirection.setImageUrl(FileUtil.getNewName(image.getOriginalFilename()));
        //调用服务添加研究方向
        researchDirectionService.addDirection(researchDirection, image);
        return Result.success();
    }

    /**
     * 修改研究方向
     * @param id 研究方向ID
     * @param title 研究方向标题
     * @param image 研究方向图片
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result updateDirection(@RequestParam ("id") Integer id,
                                  @RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "image", required = false) MultipartFile image) {
        if (id == null) {
            log.warn("未选择任何数据，ID为空");
            throw new NoIdException();
        }
        //封装研究方向对象
        String url = null;
        //如果有新图片，保存图片并获取新路径
        if (image != null && !image.isEmpty()) {
            log.info("上传文件：{}", image.getOriginalFilename());
            url = FileUtil.getNewName(image.getOriginalFilename());
        }
        ResearchDirection researchDirection = new ResearchDirection(id, title, url, null);
        researchDirectionService.updateDirection(researchDirection, image);
        return Result.success();
    }

    /**
     * 删除研究方向
     * @param id 研究方向ID
     * @return 操作结果
     */
    @DeleteMapping("")
    public Result deleteDirection(Integer id) {
        if (id == null) {
            log.warn("未选择任何数据，ID为空");
            throw new NoIdException();
        }
        researchDirectionService.deleteDirection(id);
        return Result.success();
    }
}
