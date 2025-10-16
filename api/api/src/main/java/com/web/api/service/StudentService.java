package com.web.api.service;

import com.web.api.pojo.Student;

import java.util.List;

/**
 * 在读学生服务接口
 */
public interface StudentService {

    /**
     * 获取所有在读学生信息
     * @return 在读学生列表
     */
    List<Student> getAllStudents();
}
