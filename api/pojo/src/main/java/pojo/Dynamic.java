package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 团队动态
 */

@Data
@AllArgsConstructor
public class Dynamic {
    public String qrCode;   //图片路径
    public String title;    //小标题
    public String desc;     //详情介绍
    public LocalDateTime time;  //发布时间
}
