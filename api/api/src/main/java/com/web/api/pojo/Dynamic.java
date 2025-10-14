package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 团队动态
 */
@Data
@AllArgsConstructor
public class Dynamic {
    private int id;
    private String qrCode;   //图片路径
    private String title;    //小标题
    private String desc;     //详情介绍
    private LocalDateTime time;  //发布时间
}
