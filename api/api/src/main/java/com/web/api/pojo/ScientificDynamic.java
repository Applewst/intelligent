package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 科研动态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScientificDynamic {
    private int id;
    /** 图片路径*/
    private String image;
    /** 标题 */
    private String title;
    /** 详情介绍 */
    private String detail;
    /** 发布时间 */
    private LocalDateTime time;
}
