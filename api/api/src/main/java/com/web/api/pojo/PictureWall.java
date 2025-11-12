package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 图片墙数据交互
 * @author Askr-Yggdrasill
 */
@Data
@AllArgsConstructor
public class PictureWall {
    /** ID */
    private Integer id;
    /** 标题 */
    private String title;
    /** 图片文件路径 */
    private String file;
    /** 时间 */
    private LocalDateTime time;
}
