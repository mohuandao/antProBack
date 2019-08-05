package com.example.demo.springsecurity.wdtest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .permitAll()     //允许所有请求通过
                .and()
                .csrf()
                .disable()      //禁用自带的跨域处理
                .sessionManagement()   //定制自己的session策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // 让spring security不创建session
    }
}
