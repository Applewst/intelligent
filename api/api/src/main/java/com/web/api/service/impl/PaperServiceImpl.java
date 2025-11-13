package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoFindException;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.PaperMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Paper;
import com.web.api.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * 论文服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public PageResult getAllPaper(PageQueryDTO pageQuery, String author) {
        log.info("查询论文列表, num: {}, size: {}, name: {}, author: {}", pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getName(), author);
        //1.设置分页参数
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        //2.执行查询,转为Page格式
        List<Paper> empList = paperMapper.getAllPapers(pageQuery.getName(), author);
        Page<Paper> p = (Page<Paper>) empList;
        //3.返回分页结果
        return new PageResult((long) p.getPages(),p.getResult());
    }

    @Override
    public void addPaper(Paper paper) {
        paper.setTime(LocalDate.now());
        try {
            paperMapper.addPaper(paper);
        } catch (Exception e) {
            log.error("添加论文信息时发生异常，title：{}", paper.getTitle(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void updatePaper(Paper paper) {
        log.info("更新论文信息，ID：{}, title: {}", paper.getId(), paper.getTitle());
        if (paper.getId() == null) {
            log.warn("更新论文信息时未提供ID");
            throw new NoIdException();
        }
        if (!paperMapper.existsById(paper.getId())) {
            log.warn("尝试更新不存在的论文，ID：{}, title: {}", paper.getId(), paper.getTitle());
            throw new NoFindException();
        }
        try {
            paperMapper.updatePaper(paper);
        }
        catch (Exception e) {
            log.error("更新论文信息时发生异常，ID：{}, title: {}", paper.getId(), paper.getTitle(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deletePaper(Integer id) {
        log.info("删除论文信息，ID：{}", id);
        if (id == null) {
            log.warn("删除论文信息时未提供ID");
            throw new NoIdException();
        }
        if (!paperMapper.existsById(id)) {
            log.warn("尝试删除不存在的论文，ID：{}", id);
            throw new NoFindException();
        }
        try {
            paperMapper.deletePaper(id);
        } catch (Exception e) {
            log.error("删除论文信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
    }
}
