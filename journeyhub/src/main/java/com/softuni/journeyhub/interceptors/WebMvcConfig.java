package com.softuni.journeyhub.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ForumInterceptor forumInterceptor;

    @Autowired
    public WebMvcConfig(ForumInterceptor forumInterceptor) {
        this.forumInterceptor = forumInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.forumInterceptor);
    }
}
