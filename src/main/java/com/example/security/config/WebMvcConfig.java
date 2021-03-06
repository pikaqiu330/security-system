package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class WebMvcConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1.允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2.允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3.允许任何方法（post、get等）
        //corsConfiguration.setAllowCredentials(true);
        //corsConfiguration.setMaxAge(3600l);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4.注册跨域请求配置
        return new CorsFilter(source);
    }


    
}
