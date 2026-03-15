package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.ResearchDirectionGroupMapper;
import com.web.api.pojo.*;
import com.web.api.service.ResearchDirectionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ResearchDirectionGroupServiceImpl implements ResearchDirectionGroupService {

    @Autowired
    ResearchDirectionGroupMapper researchDirectionGroupMapper;

    @Override
    public PageResult ResearchDirectionGroupList(PageQueryDTO1 pageQueryDTO1) {
        // 1.设置分页参数
        PageHelper.startPage(pageQueryDTO1.getPageNum(), pageQueryDTO1.getPageSize());
        // 2.执行查询，传递正确的参数
        List<ResearchDirectionGroup> empList = researchDirectionGroupMapper.selectAllResearchDirectionGroup(
                pageQueryDTO1.getName(),
                pageQueryDTO1.getAuthor()
        );
        // 3.类型转换
        Page<ResearchDirectionGroup> p = (Page<ResearchDirectionGroup>) empList;
        // 4.返回分页结果
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void addResearchDirectionGroup(ResearchDirectionGroup researchDirectionGroup) {
        log.info("新增接口Service");
        try {
            researchDirectionGroup.setTime(LocalDateTime.now());
            researchDirectionGroupMapper.addResearchDirectionGroup(researchDirectionGroup);
        } catch (Exception e) {
            log.error("新增异常");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateResearchDirectionGroup(ResearchDirectionGroup researchDirectionGroup) {
        try {
            researchDirectionGroup.setTime(LocalDateTime.now());
            researchDirectionGroupMapper.updateResearchDirectionGroup(researchDirectionGroup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteResearchDirectionGroup(Integer id) {
        log.info("根据id删除课题组 id:{}",id);
        try {
            researchDirectionGroupMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
