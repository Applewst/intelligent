package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.GraduatesMapper;
import com.web.api.pojo.Graduate;
import com.web.api.pojo.PageResult;
import com.web.api.service.GraduatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 毕业生服务实现类
 */
@Service
public class GraduatesServiceImpl implements GraduatesService {
    @Autowired
    private GraduatesMapper graduatesMapper;

    /**
     * 分页查询毕业生信息
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询毕业生列表
        Page<Graduate> page = graduatesMapper.pageQuery();
        // 返回分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 保存毕业生信息
     *
     * @param graduate 毕业生信息
     */
    @Override
    public void save(Graduate graduate) {
        graduatesMapper.insert(graduate);
    }

    /**
     * 根据ID更新毕业生信息
     *
     * @param graduate 毕业生信息
     */
    @Override
    public void updateById(Graduate graduate) {
        graduatesMapper.update(graduate);
    }

    /**
     * 根据ID批量删除毕业生信息
     *
     * @param ids 毕业生ID列表
     */
    @Override
    public void deleteBatchByIds(List<Integer> ids) {
        graduatesMapper.deleteBatch(ids);
    }
}
