package com.example.demo.springsecurity.wdtest.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.springsecurity.wdtest.dao.TUserMapper;
import com.example.demo.springsecurity.wdtest.model.TUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    TUserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = new TUser();
        user.setUsername(username);
        TUser tUser = userMapper.selectOne(new QueryWrapper<TUser>(user));
        if (tUser == null){
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new User(tUser.getUsername(),tUser.getPassword(),authorities);
    }
}
