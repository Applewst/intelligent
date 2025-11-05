package com.web.api.service.impl;

import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoFindException;
import com.web.api.mapper.ResearchDirectionMapper;
import com.web.api.pojo.ResearchDirection;
import com.web.api.service.ImageService;
import com.web.api.service.ResearchDirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 研究方向服务实现类
 */
@Slf4j
@Service
public class ResearchDirectionServiceImpl implements ResearchDirectionService {
    @Autowired
    private ResearchDirectionMapper researchDirectionMapper;
    @Autowired
    private ImageService imageService;

    @Override
    public List<ResearchDirection> getAllDirection() {
        log.info("查询所有研究方向信息");
        return researchDirectionMapper.getAllDirections();
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
    public void addDirection(ResearchDirection direction, MultipartFile image) {
        try {
            direction.setTime(LocalDateTime.now());
            log.info("添加新的研究方向信息，标题：{}, 时间： {}", direction.getTitle(), direction.getTime());
            //添加研究方向信息
            researchDirectionMapper.addDirection(direction);
            //保存图片
            imageService.saveImage(image, direction.getImageUrl());
        } catch (Exception e) {
            log.warn("添加研究方向信息时发生异常，标题：{}", direction.getTitle(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void updateDirection(ResearchDirection direction, MultipartFile image) {
        try {
            log.info("修改研究方向信息，ID：{}", direction.getId());
            //如果有新图片
            if (!direction.getImageUrl().isEmpty()) {
                String oldUrl = researchDirectionMapper.getImageUrlById(direction.getId());
                //删除旧图片,保存新图片
                imageService.deleteImage(oldUrl);
                imageService.saveImage(image, direction.getImageUrl());
            }
            researchDirectionMapper.motifyDirectionById(direction);
        } catch (Exception e) {
            log.error("修改研究方向信息时发生异常，ID：{}", direction.getId(), e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deleteDirection(int id) {
        String url = researchDirectionMapper.getImageUrlById(id);
        //删除研究方向信息
        try {
            log.info("删除研究方向信息，ID：{}", id);
            researchDirectionMapper.deleteDirectionById(id);
        } catch (Exception e) {
            log.error("删除研究方向信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
        //删除关联的图片
        imageService.deleteImage(url);
    }
}
