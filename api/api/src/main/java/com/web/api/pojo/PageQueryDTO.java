package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryDTO {
    // 页码
    private Integer pageNum = 1;
    // 每页大小
    private Integer pageSize = 10;
    // 根据名称搜索
    private String name;

    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}

