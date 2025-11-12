package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
    /** 联系方式 */
    private String email;
    /** 位置 */
    private String location;
    /** 位置图片 */
    private String locationImage;
    /** 微信 */
    private String wechatAccount;
    /** 微信图片 */
    private String wechatQrCode;
}
