package com.web.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.api.mapper.TeacherMapper;
import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Teacher;
import com.web.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师服务实现类
 */
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 教师分页查询
     *
     * @param pageQueryDTO 分页查询参数
     * @return 分页结果
     */
    @Override
    public PageResult page(PageQueryDTO pageQueryDTO) {
        // 设置分页参数
        PageHelper.startPage(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        // 查询教师列表
        Page<Teacher> page = teacherMapper.page(pageQueryDTO);
        // 返回分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID查询教师
     *
     * @param id 教师ID
     * @return 教师信息
     */
    @Override
    public Teacher getById(Integer id) {
        return teacherMapper.getById(id);
    }

    /**
     * 保存教师信息
     *
     * @param teacher 教师信息
     */
    @Override
    public void save(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    /**
     * 根据ID更新教师信息
     *
     * @param teacher 教师信息
     */
    @Override
    public void updateById(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    /**
     * 根据ID批量删除教师
     *
     * @param ids 教师ID列表
     */
    @Override
    public void deleteBatchByIds(List<Integer> ids) {
        teacherMapper.deleteBatch(ids);
    }

    /**
     * 获取所有教师信息
     *
     * @return 教师列表
     */
    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }
}
