package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.PictureWallMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.PictureWall;
import com.web.api.service.PictureWallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图片墙服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class PictureWallServiceImpl implements PictureWallService {

    @Autowired
    private PictureWallMapper pictureWallMapper;

    @Override
    public PageResult getPictureWallList(PageQueryDTO pageQuery) {
        log.info("查询论文列表, num: {}, size: {}, name: {}", pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getName());
        //1.设置分页参数
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        //2.执行查询,转为Page格式
        List<PictureWall> empList = pictureWallMapper.getPictureWall(pageQuery.getName());
        Page<PictureWall> p = (Page<PictureWall>) empList;
        //3.返回分页结果
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void addPictureWall(PictureWall pictureWall) {
        log.info("添加图片墙信息，信息：{}", pictureWall);
        try {
            pictureWall.setTime(LocalDateTime.now());
            pictureWallMapper.addPictureWall(pictureWall);
        } catch (Exception e) {
            log.error("添加图片墙信息时发生异常，信息：{}", pictureWall, e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void updatePictureWall(PictureWall pictureWall) {
        log.info("修改图片墙信息，信息：{}", pictureWall);
        if (pictureWall.getId() == null) {
            log.warn("修改图片墙信息时发生异常，ID不能为空，信息：{}", pictureWall);
            throw new NoIdException();
        }
        try {
            pictureWallMapper.updatePictureWall(pictureWall);
        } catch (Exception e) {
            log.error("修改图片墙信息时发生异常，信息：{}", pictureWall, e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deletePictureWall(Integer id) {
        log.info("删除图片墙信息，ID：{}", id);
        if (id == null) {
            log.warn("删除图片墙信息时发生异常，ID不能为空");
            throw new NoIdException();
        }
        try {
            pictureWallMapper.deletePictureWall(id);
        } catch (Exception e) {
            log.error("删除图片墙信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
    }
}
