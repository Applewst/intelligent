package com.web.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生成长
 * @author Askr-Yggdrasill
 */
@Data
@AllArgsConstructor
public class StudentDevelopment {
    /** ID */
    private Integer id;
    /** 姓名 */
    private String name;
    /** 文件URL */
    private String file;
    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    /** 详情 */
    private String detail;
}
