package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 团队介绍实体类
 * @author Askr-Yggdrasill
 */
@Data
@AllArgsConstructor
public class Team {
    private Integer id;
    private String teamName;
    private String introduction;
    private String imageUrl;
}
