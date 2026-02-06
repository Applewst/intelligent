package com.web.api.mapper;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageQueryDTO1;
import com.web.api.pojo.ResearchDirectionGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResearchDirectionGroupMapper {
    /**
    根据id删除
     **/
    int deleteByPrimaryKey(Integer id);

    /**分页查询接口
     *
     * @return
     */
    List<ResearchDirectionGroup> selectAllResearchDirectionGroup(String name,String author);

    /**增加接口
     *
     * @param researchDirectionGroup
     */
   void addResearchDirectionGroup(ResearchDirectionGroup researchDirectionGroup);

    /**更新接口
     *
     * @param researchDirectionGroup
     */
   void updateResearchDirectionGroup(ResearchDirectionGroup researchDirectionGroup);

}
