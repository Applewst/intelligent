package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchDirectionDevelopment {
    private int id;
    private String name;
    private String title;
    private String description;
    private String time;
}
