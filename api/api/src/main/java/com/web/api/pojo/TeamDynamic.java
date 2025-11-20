package com.web.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 团队活动类
 */
@Data
@AllArgsConstructor
public class TeamDynamic {
    private int id;
    /** 标题 */
    private String image;
    /** 详情介绍 */
    private String title;
    /** 发布时间 */
    private String detail;
    /** 图片路径 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
