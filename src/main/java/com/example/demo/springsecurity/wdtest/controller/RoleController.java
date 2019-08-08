package com.example.demo.springsecurity.wdtest.controller;

import com.example.demo.springsecurity.wdtest.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @program: spring-security
 * @description: 角色维护,用户角色维护
 * @author: wdong
 * @date 2019/8/5 20:34
 **/
@RestController
public class RoleController {
    public static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping("getRoleIdsByUserId")
    public HashMap<String,Object> getRoleIdsByUserId(@RequestParam Long userId){
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Integer> roleIds = roleService.getRoleIdByUserId(userId);
        map.put("userId",userId);
        map.put("roleIds",roleIds);
        return map;
    }

    @Transactional
    @PostMapping("updateRoleIdsByUserId")
    public HashMap<String,Object> updateRoleIdsByUserId(@RequestBody HashMap<String,Object> map){
        HashMap<String, Object> result = new HashMap<>();
        Long userId = map.get("userId") ==null ? null:Long.parseLong(map.get("userId").toString());
        List<Integer> roleIds = map.get("roleIds") ==null ? null: (List<Integer>) map.get("roleIds");
        roleService.deleteRoleIdByUserId(userId);

        roleService.addRoleIdByUserId(userId,roleIds);
        result.put("status","ok");
        return result;
    }
}
