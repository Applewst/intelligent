package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 研究方向类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchDirection {
    private int id;
    /** 标题 */
    private String title;
    /** 图片路径 */
    private String imageUrl;
}
