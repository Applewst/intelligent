package com.web.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 研究方向类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchDirection {
    private Integer id;
    /** 标题 */
    private String title;
    /** 图片路径 */
    private String imageUrl;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
