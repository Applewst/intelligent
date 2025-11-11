package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private String id;
    private String name;
    private String size;
    private String type;
    private String url;
    private String updateTime;
    private String downloads;
}
