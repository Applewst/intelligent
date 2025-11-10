package com.web.api.mapper;

import com.web.api.pojo.ResearchDirection;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 研究方向数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface ResearchDirectionMapper {

    /**
     * 根据ID检查研究方向是否存在
     * @param id 研究方向ID
     * @return 是否存在
     */
    Boolean existsById(int id);

    /**
     * 查询所有研究方向
     * @return 研究方向列表
     */
    List<ResearchDirection> getAllDirections(String name);

    /**
     * 根据ID查询研究方向
     * @param id 研究方向ID
     * @return 研究方向
     */
    ResearchDirection getDirectionById(int id);

    /**
     * 根据ID获取图片URL
     * @param id 研究方向ID
     * @return 图片URL
     */
    String getImageUrlById(int id);

    /**
     * 根据ID修改研究方向
     * @param researchDirection 研究方向信息
     */
    void motifyDirectionById(ResearchDirection researchDirection);

    /**
     * 根据ID删除研究方向
     * @param id 研究方向ID
     */
    void deleteDirectionById(int id);

    /**
     * 添加研究方向
     * @param researchDirection 研究方向信息
     */
    void addDirection(ResearchDirection researchDirection);
}
