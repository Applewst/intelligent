package com.web.api.service;

import com.web.api.pojo.Result;
import com.web.api.pojo.Teacher;

import java.util.List;

/**
 * 教师详情接口
 */
public interface TeacherService {

/**
 * 展示所有教师信息
 */
     public List<Teacher> getAllTeachers();

     public Teacher getTeacherById(int id);
}
