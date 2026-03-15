package com.web.api.controller;

import com.web.api.exception.NoIdException;
import com.web.api.pojo.PageQueryDTO1;
import com.web.api.pojo.ResearchDirectionGroup;
import com.web.api.pojo.Result;
import com.web.api.service.ResearchDirectionGroupService;
import com.web.api.service.impl.ResearchDirectionGroupServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/search/direction")
public class ResearchDirectionGroupController {

        @Autowired
        private ResearchDirectionGroupService researchDirectionGroupService;

    @GetMapping("")
    public Result getResearchDirectionGroup(PageQueryDTO1 pageQuery)
    {
        log.info("分页查询研究方向课题");
        return Result.success(researchDirectionGroupService.ResearchDirectionGroupList(pageQuery));
    }

    @PostMapping("")
    public Result saveResearchDirectionGroup(@RequestBody ResearchDirectionGroup researchDirectionGroup){
        log.info("新增接口");
        researchDirectionGroupService.addResearchDirectionGroup(researchDirectionGroup);
        return Result.success();
    }

    @PutMapping("")
    public Result putchResearchDirectionGroup(@RequestBody ResearchDirectionGroup researchDirectionGroup)
    {
    log.info("根据id修改接口 id:{}",researchDirectionGroup.getId());
        if (researchDirectionGroup.getId() == null) {
            log.warn("修改研究方向:未选择任何数据，ID为空");
            throw new NoIdException();
        }
    researchDirectionGroupService.updateResearchDirectionGroup(researchDirectionGroup);
        return Result.success();
    }
    @DeleteMapping("")
    public Result deleteResearchDirectionGroup(@RequestParam Integer id)
        {
            log.info("删除接口");
            if(id==null){
                log.warn("删除研究课题组:未选择任何数据，ID为空");
                throw new NoIdException();
            }
            researchDirectionGroupService.deleteResearchDirectionGroup(id);
            return Result.success();
        }
}
