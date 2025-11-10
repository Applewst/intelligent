package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoFindException;
import com.web.api.mapper.ResearchDirectionMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ResearchDirection;
import com.web.api.service.ImageService;
import com.web.api.service.ResearchDirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 研究方向服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class ResearchDirectionServiceImpl implements ResearchDirectionService {
    @Autowired
    private ResearchDirectionMapper researchDirectionMapper;
    @Autowired
    private ImageService imageService;

    @Override
    public PageResult getAllDirections(int pageNum, int pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageNum, pageSize);
        //2.执行查询,转为Page格式
        List<ResearchDirection> empList = researchDirectionMapper.getAllDirections();
        Page<ResearchDirection> p = (Page<ResearchDirection>) empList;
        //3.返回分页结果
        return new PageResult((long) p.getPages(),p.getResult());
    }

    @Override
    public ResearchDirection getDirectionById(int id) {

        ResearchDirection researchDirection;
        log.info("根据ID查询研究方向信息，ID：{}", id);
        try {
            researchDirection = researchDirectionMapper.getDirectionById(id);
        } catch (Exception e) {
            log.error("查询研究方向信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
        if (researchDirection == null) {
            log.warn("未找到ID为{}的研究方向信息", id);
            throw new NoFindException();
        }
        return researchDirection;
    }

    @Override
    public void addDirection(ResearchDirection direction) {
        try {
            direction.setTime(LocalDateTime.now());
            log.info("添加新的研究方向信息，标题：{}, 时间： {}", direction.getTitle(), direction.getTime());
            //添加研究方向信息
            researchDirectionMapper.addDirection(direction);
        } catch (Exception e) {
            log.warn("添加研究方向信息时发生异常，标题：{}", direction.getTitle(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void updateDirection(ResearchDirection direction) {
        log.info("修改研究方向信息，ID：{}", direction.getId());
        if (!researchDirectionMapper.existsById(direction.getId())) {
            log.warn("未找到ID为{}的研究方向信息，无法修改", direction.getId());
            throw new NoFindException();
        }
        try {
            //如果有新图片
            if (!direction.getImage().isEmpty()) {
                //删除旧图片
                String oldUrl = researchDirectionMapper.getImageUrlById(direction.getId());
                imageService.deleteImage(oldUrl);
            }
            //更新研究方向信息
            researchDirectionMapper.motifyDirectionById(direction);
        } catch (Exception e) {
            log.error("修改研究方向信息时发生异常，ID：{}", direction.getId(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deleteDirection(int id) {
        log.info("删除研究方向信息，ID：{}", id);
        if (!researchDirectionMapper.existsById(id)) {
            log.warn("未找到ID为{}的研究方向信息，无法删除", id);
            throw new NoFindException();
        }
        String url = researchDirectionMapper.getImageUrlById(id);
        //删除研究方向信息
        try {
            researchDirectionMapper.deleteDirectionById(id);
        } catch (Exception e) {
            log.error("删除研究方向信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
        //删除关联的图片
        imageService.deleteImage(url);
    }
}
