package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Paper;

/**
 * 论文服务接口
 * @author Askr-Yggdrasill
 */
public interface PaperService {

    /**
     * 分页查询所有论文
     * @param pageQueryDTO 分页查询参数
     * @return 论文列表
     */
    PageResult getAllPaper(PageQueryDTO pageQueryDTO, String author, String title);

    /**
     * 添加论文
     * @param paper 论文信息
     */
    void addPaper(Paper paper);

    /**
     * 更新论文
     * @param paper 论文信息
     */
    void updatePaper(Paper paper);

    /**
     * 删除论文
     * @param id 论文ID
     */
    void deletePaper(Integer id);
}
