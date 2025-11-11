package com.web.api.service;
//气死我了  这臭接口怎么这么多
import com.web.api.pojo.PageResult;
import com.web.api.pojo.Result;
import com.web.api.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
    动态服务接口
 */
public interface DynamicService<T> {
    /**
     * 获取所有动态信息
     */

    public List<T> getAllDynamics();
    /**.
     * 获取最新动态
     */

    public List<T> newDynamics(int num);

    /**
     * 更新动态
     * @param
     */

    void updateDynamics(int id,String title,String image,String detail,String time);

    /**
     * 删除动态
     * @param
     */

    void deleteDynamics(int id);

    /**
     *新增动态
      */

    void saveDynamics(String image, String title, String detail);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult pageQuery(Integer pageNum, Integer pageSize,String title);
}
