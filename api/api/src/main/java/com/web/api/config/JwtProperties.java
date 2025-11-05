package com.web.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.io.Serializable;

/**
 * JWT配置类
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties implements Serializable {

    /**
     * 签名密码,在配置文件设置
     */
    private String base64EncodeSecretKey;
}
