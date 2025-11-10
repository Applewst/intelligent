package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ResearchDirection;

/**
 * 研究方向服务接口
 */
public interface ResearchDirectionService {

    /**
     * 获取所有研究方向
     * @return 研究方向列表
     */
    PageResult getAllDirections(PageQueryDTO pageQueryDTO);

    /**
     * 根据ID获取研究方向
     * @param id 研究方向ID
     * @return 研究方向
     */
    ResearchDirection getDirectionById(int id);

    /**
     * 添加新的研究方向
     * @param direction 研究方向对象
     */
    void addDirection(ResearchDirection direction);

    /**
     * 修改研究方向
     * @param direction 研究方向对象
     */
     void updateDirection(ResearchDirection direction);

    /**
     * 删除研究方向
     * @param id 研究方向ID
     */
    void deleteDirection(int id);
}
