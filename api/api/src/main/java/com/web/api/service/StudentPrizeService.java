package com.web.api.service;

import com.web.api.pojo.PageQueryDTO;
import com.web.api.pojo.PageResult;
import com.web.api.pojo.StudentPrize;

/**
 * 学生获奖服务接口
 * @author Askr-Yggdrasill
 */
public interface StudentPrizeService {

    PageResult getAllStudentPrizes(PageQueryDTO pageQueryDTO);

    void addStudentPrize(StudentPrize studentPrize);

    void motifyStudentPrizeById(StudentPrize studentPrize);

    void deleteStudentPrizeById(Integer id);
}
