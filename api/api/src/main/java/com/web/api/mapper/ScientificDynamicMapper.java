package com.web.api.mapper;
//好烦好烦好烦！！！！
import com.github.pagehelper.Page;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 科研动态数据交互
 */
@Mapper
public interface ScientificDynamicMapper {

    /**
     * 查询所有科研动态
     */
     List<ScientificDynamic> getAllDynamics();

    /**
     * 查询最新int条动态
     */
     List<ScientificDynamic> selectNewDynamics(@Param("num") int num);

    /**
     * 获取科研动态列表
     */
    List<ScientificDynamic> getDynamicslist(@Param("pageNum") int pageNum, @Param("size") int pageSize);

    /**
     *新增科研动态
     */
    @Insert("insert into web.scientific_dynamic(image, title, detail)"+
            "values(#{image},#{title},#{detail})")
    void saveDynamics(@Param("image") String image,
                      @Param("title") String title,
                      @Param("detail") String detail);

    /**
     * 根据id删除科研动态
     */
     void deleteDynamics(@Param("id") int id);
    /**
     *根据id修改科研动态
     */
    void updateDynamics(@RequestParam int id,
                        @RequestParam String image,
                        @RequestParam String title,
                        @RequestParam String detail,
                        @RequestParam String time);

    /**
     * 返回列表
     * @return
     */
    Page<ScientificDynamic> pageQuery(@Param("title") String title);
}
