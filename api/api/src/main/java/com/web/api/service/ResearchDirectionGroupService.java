package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageQueryDTO1;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ResearchDirectionGroup;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ResearchDirectionGroupService {

    /**
     * 分页查询
     * @param pageQueryDTO1
     * @return
     */
    PageResult ResearchDirectionGroupList(PageQueryDTO1 pageQueryDTO1);

    void addResearchDirectionGroup(@RequestBody ResearchDirectionGroup researchDirectionGroup);

    void updateResearchDirectionGroup(@RequestBody ResearchDirectionGroup researchDirectionGroup);

    void deleteResearchDirectionGroup(@RequestParam Integer id);

}
