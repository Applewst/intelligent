package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.ScientificDynamicMapper;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.pojo.Student;
import com.web.api.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 科研动态服务实现类
 */
@Slf4j
@Service
public class ScientificDynamicServiceImpl implements DynamicService<ScientificDynamic> {

    @Autowired
    private ScientificDynamicMapper scientificDynamicMapper;

    @Override
    public List<ScientificDynamic> getAllDynamics() {
        log.info("查询所有科研动态信息");
        return scientificDynamicMapper.getAllDynamics();
    }

    @Override
    public List<ScientificDynamic> newDynamics(int num) {
        log.info("查询最新的{}条科研动态信息", num);
        return scientificDynamicMapper.selectNewDynamics(num);
    }

    @Override
    public void updateDynamics(int id,String title,String image,String detail,String time) {
        scientificDynamicMapper.updateDynamics(id,title,image,detail,time);
        log.info("更新科研动态");
    }

    @Override
    public void deleteDynamics(int id) {
    log.info("删除科研动态");
    scientificDynamicMapper.deleteDynamics(id);
    }

    @Override
    public void saveDynamics(@Param("image") String image,@Param("title") String title,@Param("detail") String detail) {
    log.info("新增科研动态");
    scientificDynamicMapper.saveDynamics(image,title,detail);
    }

    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize,String title) {
        PageHelper.startPage(pageNum, pageSize);
        Page<ScientificDynamic> page = scientificDynamicMapper.pageQuery(title);
        log.info("分页查询结果，标题条件：{}", title);
        log.info("分页查询结果");
        return new PageResult(page.getTotal(), page.getResult());
    }
}
