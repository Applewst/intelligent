package com.web.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 允许所有域名进行跨域调用
        config.setAllowCredentials(true); // 允许跨越发送cookie
        config.addAllowedMethod("*"); // 允许所有请求方法
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addExposedHeader("*"); // 暴露所有头信息

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 基于url的跨域配置源
        source.registerCorsConfiguration("/**", config); // 对所有路径应用该配置

        return new CorsFilter(source);
    }
}
