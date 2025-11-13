package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * 论文实体类
 */
@Data
@AllArgsConstructor
public class Paper {
    /** 论文ID */
    private Integer id;
    /** 论文标题 */
    private String title;
    /** 论文作者 */
    private String author;
    /** 时间时间 */
    private LocalDate time;
    /** 文件URL */
    private String file;
    /** 论文描述 */
    private String detail;
}
