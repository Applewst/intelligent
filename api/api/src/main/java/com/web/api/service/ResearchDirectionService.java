package com.web.api.service;

import com.web.api.pojo.ResearchDirection;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 研究方向服务接口
 */
public interface ResearchDirectionService {

    /**
     * 获取所有研究方向
     * @return 研究方向列表
     */
    public List<ResearchDirection> getAllDirection();

    /**
     * 根据ID获取研究方向
     * @param id 研究方向ID
     * @return 研究方向
     */
    public ResearchDirection getDirectionById(int id);

    /**
     * 添加新的研究方向
     * @param direction 研究方向对象
     */
    public void addDirection(ResearchDirection direction, MultipartFile image);

    /**
     * 修改研究方向
     * @param direction 研究方向对象
     */
    public void updateDirection(ResearchDirection direction, MultipartFile image);

    /**
     * 删除研究方向
     * @param id 研究方向ID
     */
    public void deleteDirection(int id);
}
