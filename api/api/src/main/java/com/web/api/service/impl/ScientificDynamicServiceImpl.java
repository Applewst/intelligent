package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.ScientificDynamicMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.ScientificDynamic;
import com.web.api.service.ScientificDynamicService;
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
public class ScientificDynamicServiceImpl implements ScientificDynamicService {

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
    public void updateDynamics(ScientificDynamic scientificDynamic) {
        scientificDynamicMapper.updateDynamics(scientificDynamic);
        log.info("更新科研动态");
    }

    @Override
    public void deleteDynamics(Integer id) {
        if( id == null ){
            log.error("id为空删除,删除错误");
            throw new NoIdException();
        } else if (id<=0) {
            log.error("id错误");
            throw new IllegalArgumentException("无效的ID: " + id);
        } else{
            log.info("删除科研动态");
              scientificDynamicMapper.deleteDynamics(id);
        }
    }

    @Override
    public void saveDynamics(ScientificDynamic scientificDynamic) {
    log.info("新增科研动态");
    scientificDynamicMapper.saveDynamics(scientificDynamic);
    }

    @Override
    public PageResult pageQuery(int pageNum,int pageSize,String title) {
        PageHelper.startPage(pageNum,pageSize);
        Page<ScientificDynamic> page = scientificDynamicMapper.pageQuery(title);
        log.info("分页查询结果，title：{}",title);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
