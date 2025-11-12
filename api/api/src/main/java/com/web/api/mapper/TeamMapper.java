package com.web.api.mapper;

import com.web.api.pojo.Team;
import org.apache.ibatis.annotations.Mapper;

/**
 * 团队介绍数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface TeamMapper {

    /**
     * 查询所有团队成员
     * @return 团队成员列表
     */
    Team selectAll();

    /**
     * 更新团队信息
     * @param team 团队信息
     */
    void updateTeam(Team team);
}
