package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
    private String grade;       //年级
    private String identity;    //权限身份
    private String major;   //专业
    private String avatar;  //头像地址
}
