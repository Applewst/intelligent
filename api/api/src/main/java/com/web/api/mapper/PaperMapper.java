package com.web.api.mapper;

import com.web.api.pojo.Paper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 论文数据交互
 * @author Askr-Yggdrasill
 */
@Mapper
public interface PaperMapper {

    /**
     * 获取所有论文，支持按名称和作者过滤
     * @param name 论文名称过滤条件
     * @param author 论文作者过滤条件
     * @return 论文列表
     */
    List<Paper> getAllPapers(String name, String author);

    /**
     * 添加论文
     * @param paper 论文信息
     */
    void addPaper(Paper paper);

    /**
     * 更新论文信息
     * @param paper 论文信息
     */
    void updatePaper(Paper paper);

    /**
     * 删除论文
     * @param id 论文ID
     */
    void deletePaper(int id);

    /**
     * 根据ID检查论文是否存在
     * @param id 论文ID
     * @return 是否存在
     */
    boolean existsById(int id);
}
