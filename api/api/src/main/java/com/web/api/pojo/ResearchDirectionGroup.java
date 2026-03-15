package com.web.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchDirectionGroup {
    //id
    private Integer id;
    //名字
    private String name;
    //细节
    private String detail;
    //作者
    private String author;
    //文件url
    private String file;
    //时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    //总体
    private String total;
}
