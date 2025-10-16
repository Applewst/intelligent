package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo {
    private int id;
    private String name;
    private String title;
    private String email;
    private String avatar;
    private String info;
}
