package com.web.api.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    // 总页数
    private Long total;
    // 当前页数据集合
    private List data;
}
