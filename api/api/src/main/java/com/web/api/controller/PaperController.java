package com.web.api.controller;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Paper;
import com.web.api.pojo.Result;
import com.web.api.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 论文控制器
 * @author Askr-Yggdrasill
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class PaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 获取论文列表
     * @param pageQuery 分页查询参数
     * @param author 论文作者
     * @return 论文列表
     */
    @GetMapping("/papers/list")
    public Result getPaperList(PageQueryDTO pageQuery, String author) {
        log.info("获取论文列表");
        return Result.success(paperService.getAllPaper(pageQuery, author));
    }

    /**
     * 添加论文
     * @param p 论文信息
     * @return 操作结果
     */
    @PostMapping("/papers")
    public Result addPaper(@RequestBody Paper p) {
        log.info("添加论文");
        paperService.addPaper(p);
        return Result.success();
    }

    /**
     * 更新论文信息
     * @param p 论文信息
     * @return 操作结果
     */
    @PutMapping("/papers")
    public Result updatePaper(@RequestBody Paper p) {
        log.info("更新论文信息");
        paperService.updatePaper(p);
        return Result.success();
    }

    /**
     * 删除论文
     * @param p 论文信息
     * @return 操作结果
     */
    @DeleteMapping("/papers")
    public Result deletePaper(Paper p) {
        log.info("删除论文");
        paperService.deletePaper(p.getId());
        return Result.success();
    }
}
