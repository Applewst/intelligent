package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.exception.DatabaseOperationException;
import com.web.api.exception.NoIdException;
import com.web.api.mapper.StudentPrizeMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.StudentPrize;
import com.web.api.service.StudentPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * 学生获奖服务实现类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
public class StudentPrizeServiceImpl implements StudentPrizeService {

    @Autowired
    private StudentPrizeMapper studentPrizeMapper;

    @Override
    public PageResult getAllStudentPrizes(PageQueryDTO pageQuery) {
        log.info("查询学生获奖列表, num: {}, size: {}, name: {}", pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getName());
        //1.设置分页参数
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        //2.执行查询,转为Page格式
        List<StudentPrize> empList = studentPrizeMapper.getAllStudentPrizes(pageQuery.getName());
        Page<StudentPrize> p = (Page<StudentPrize>) empList;
        //3.返回分页结果
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void addStudentPrize(StudentPrize studentPrize) {
        try {
            studentPrizeMapper.addStudentPrize(studentPrize);
        } catch (Exception e) {
            log.error("添加学生获奖信息时发生异常，信息：{}", studentPrize, e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void motifyStudentPrizeById(StudentPrize studentPrize) {
        if (studentPrize.getId() == null) {
            log.warn("修改学生获奖信息时发生异常，ID不能为空，信息：{}", studentPrize);
            throw new NoIdException();
        }
        try {
            studentPrizeMapper.motifyStudentPrizeById(studentPrize);
        } catch (Exception e) {
            log.error("修改学生获奖信息时发生异常，信息：{}", studentPrize, e);
            throw new DatabaseOperationException();
        }
    }

    @Override
    public void deleteStudentPrizeById(Integer id) {
        if (id == null) {
            log.warn("删除学生获奖信息时发生异常，ID不能为空");
            throw new NoIdException();
        }
        try {
            studentPrizeMapper.deleteStudentPrizeById(id);
        } catch (Exception e) {
            log.error("删除学生获奖信息时发生异常，ID：{}", id, e);
            throw new DatabaseOperationException();
        }
    }
}
