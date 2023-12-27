package com.diary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Value("${custom.file.path.profile}")
    private String profilePath;
    @Value("${custom.file.path.background}")
    private String backgroundPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //프로필
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:"+profilePath);
    }
}
