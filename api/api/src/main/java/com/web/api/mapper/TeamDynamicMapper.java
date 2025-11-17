package com.web.api.mapper;

import com.github.pagehelper.Page;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.Student;
import com.web.api.pojo.TeamDynamic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团队活动数据交互
 */
@Mapper
public interface TeamDynamicMapper {

    /**
     * 查询所有团队动态
     */
    List<TeamDynamic> getAllDynamics();

    /**
     * 查询最新{num}条动态
     */
    List<TeamDynamic> getNewDynamic(@Param("num") int num);

    /**
     * 新增活动动态列表
     */
    @Insert("insert into team_dynamic(image, title, detail)"+
            "values(#{image},#{title},#{detail})")
    void saveTeamDynamics(@Param("image") String image,
                      @Param("title") String title,
                      @Param("detail") String detail);

    /**
     * 删除团队动态
     */
    void deleteTeamDynamic(int id);

    /**
     *修改团队动态
     */
    void updateTeamDynamic(int id,String title,String image,String detail,String time);

    /**
     * 返回列表数据
     * @return
     */
    Page<TeamDynamic> pageQuery(String title);
}
