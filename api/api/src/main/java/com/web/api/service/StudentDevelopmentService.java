package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.StudentDevelopment;

/**
 * 学生成长业务逻辑接口
 * @author Askr-Yggdrasill
 */
public interface StudentDevelopmentService {

    PageResult getAllStudentDevelopment(PageQueryDTO pageQuery);

    void addStudentDevelopment(StudentDevelopment studentDevelopment);

    void motifyStudentDevelopmentById(StudentDevelopment studentDevelopment);

    void deleteStudentDevelopmentById(Integer id);
}
