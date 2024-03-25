package com.example.daegubusapi.config;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("https://zzoe2346.github.io/", "http://localhost:63342") // 허용할 오리진(Origin)을 설정
                .allowedOrigins("*") // 모든 도메인 허용...
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드를 설정
                .allowedHeaders("*"); // 허용할 헤더를 설정
    }


}
