package com.web.api.controller;

import com.web.api.exception.NoIdException;
import com.web.api.pojo.ResearchDirection;
import com.web.api.pojo.Result;
import com.web.api.service.ResearchDirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 研究方向
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class ResearchDirectionController {

    @Autowired
    private ResearchDirectionService researchDirectionService;

    /**
     * 分页查询所有研究方向
     * @return 研究方向列表
     */
    @GetMapping("/all")
    public Result getAllDirections(int pageNum, int pageSize) {
        return Result.success(researchDirectionService.getAllDirections(pageNum, pageSize));
    }

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
     * @param researchDirection 研究方向对象
     * @return 操作结果
     */
    @PostMapping("")
    public Result addDirection(ResearchDirection researchDirection) {
        //调用服务添加研究方向
        researchDirectionService.addDirection(researchDirection);
        return Result.success();
    }

    /**
     * 修改研究方向
     * @param researchDirection 研究方向对象
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result updateDirection(ResearchDirection researchDirection) {
        if (researchDirection.getId() == null) {
            log.warn("未选择任何数据，ID为空");
            throw new NoIdException();
        }
        researchDirectionService.updateDirection(researchDirection);
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
