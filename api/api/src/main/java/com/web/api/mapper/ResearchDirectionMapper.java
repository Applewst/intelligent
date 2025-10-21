package com.web.api.mapper;

import com.web.api.pojo.ResearchDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 研究方向数据交互
 */
@Mapper
public interface ResearchDirectionMapper {

    /**
     * 查询所有研究方向
     */
    List<ResearchDirection> getAllDirections();
}
