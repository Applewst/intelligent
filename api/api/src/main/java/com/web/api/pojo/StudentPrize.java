package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

/**
 * 学生获奖实体类
 * @author Askr-Yggdrasill
 */
@Data
@AllArgsConstructor
public class StudentPrize {
    /** ID */
    private Integer id;
    /** 奖项人 */
    private String author;
    /** 文件URL */
    private String file;
    /** 获奖时间 */
    private LocalDate time;
    /** 总数 */
    private Integer total;
    /** 详情 */
    private String detail;

}
