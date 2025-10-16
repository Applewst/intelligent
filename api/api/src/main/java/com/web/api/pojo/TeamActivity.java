package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Data
@AllArgsConstructor
public class TeamActivity {
    private int id;
    private String title;
    private String description;
    private String time;
    private String img;
}
