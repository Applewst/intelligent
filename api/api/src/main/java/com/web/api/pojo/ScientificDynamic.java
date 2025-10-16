package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 科研动态
 */
@Data
@AllArgsConstructor
public class ScientificDynamic {
    private int id;
    /** 图片路径*/
    private String qrCode;
    /** 标题 */
    private String title;
    /** 详情介绍 */
    private String desc;
    /** 发布时间 */
    private LocalDateTime time;
}
