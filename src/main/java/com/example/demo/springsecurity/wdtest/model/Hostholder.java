package com.example.demo.springsecurity.wdtest.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class Hostholder {
    private static ThreadLocal<HashMap<String,Object>> users = new ThreadLocal<>();

    public HashMap<String,Object> getUsers(){
        return users.get();
    }

    public void setUsers(HashMap<String,Object> map){
        users.set(map);
    }

    public void clear(){
        users.remove();
    }
}
