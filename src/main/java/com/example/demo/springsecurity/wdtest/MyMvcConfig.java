package com.example.demo.springsecurity.wdtest;

import com.example.demo.springsecurity.intercepter.PasswordIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/** 
* @Description: 拦截器的配置
* @Author: wdong 
* @Date: 2019/8/1 
*/ 
@Component
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Autowired
    private PasswordIntercepter passwordIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passwordIntercepter).excludePathPatterns("/login");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
