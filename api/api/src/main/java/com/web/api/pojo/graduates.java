package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class graduates {
    private int id;
    private String name;
    private String grade;
    private String major;
    private String avatar;
}
