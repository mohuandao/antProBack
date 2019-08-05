package com.example.demo.springsecurity.wdtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.springsecurity.common.entity.Result;
import com.example.demo.springsecurity.common.entity.StatusCode;
import com.example.demo.springsecurity.wdtest.dao.TUserMapper;
import com.example.demo.springsecurity.wdtest.model.Hostholder;
import com.example.demo.springsecurity.wdtest.model.TUser;
import com.example.demo.springsecurity.wdtest.service.RoleService;
import com.example.demo.springsecurity.wdtest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private Hostholder hostholder;

    @Autowired
    private RoleService roleService;
   /* @RequestMapping("/")
    @Transactional
    public TUser getUser(){

        return userMapper.selectOne(new QueryWrapper<TUser>().lambda()
                .eq(TUser::getUsername,"wd"));
    }*/

    /**
     * 注册,前台页面暂时未用到此接口
     * @param username
     * @param password
     * @param remember
     * @param response
     * @return
     */
    @PostMapping(value = "/register")
    public Result register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "remember",defaultValue ="false" ) Boolean remember,
                           HttpServletResponse response){

        Map<String, Object> map = userService.register(username, password);
        try {
            if (map.containsKey("ticket")){//已登录成功
                String ticket = map.get("ticket").toString();
                Cookie cookie = new Cookie("ticket", ticket);
                cookie.setPath("/");
                if(remember){
                    cookie.setMaxAge(7*24*3600);
                }
                response.addCookie(cookie);
                return new Result(StatusCode.OK,true,"注册成功");
            }else {
                return new Result(StatusCode.ERROR,false,map.get("msg").toString());
            }
        } catch (Exception e) {
            log.error("注册异常"+e.getMessage());
            return new Result(StatusCode.ERROR,false,"注册失败");
        }

    }
    /** 
    * @Description: 登陆接口,返回ticket和用户角色信息 
    * @Param: [username, password, remember, type] 
    * @return: java.util.Map 
    * @Author: wdong 
    * @Date: 2019/8/1 
    */ 
    @PostMapping(value = "/login")
    public Map login(@RequestParam(value = "userName" ,required = true) String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember",defaultValue ="false" ) Boolean remember,
                        @RequestParam(value = "type",defaultValue = "account") String type){
        HashMap<String, Object> result = new HashMap<>(); //返回结果
        Map<String, Object> map = userService.login(username, password);
        try {
            if (map.containsKey("ticket")){
                TUser user = (TUser) map.get("user");
                String ticket = map.get("ticket").toString();
                result.put("ticket",ticket); //返回tk
                result.put("type",type);
                result.put("status","ok");
                //result.put("currentAuthority","admin");  //暂写死,根据用户查出角色
                result.put("currentAuthority",roleService.getRoleIdByUserId(user.getUid()));//存角色ID
                return result;
            }else {
                log.error("异常"+map.get("msg"));
                return null;
            }
        } catch (Exception e) {
            log.error("登陆异常"+e.getMessage());
            return null;
        }


    }
//    @PostMapping(value = "/logout")
//    public Result logout(@CookieValue(value = "ticket") String ticket){
//
//    }

//    @GetMapping(value = "/currentUser")
////    public TUser getCurrentUser(@CookieValue("ticket") String ticket){
////        if(StringUtils.isEmpty(ticket)){
////            log.error("ticket不存在");
////        }
////        return  userService.getCurrentUser(ticket);
////    }


    @GetMapping(value = "/currentUser1")
    public Map<String,Object> getCurrentUser(@RequestHeader("Authorization")String ticket,
                                             HttpServletResponse response) throws IOException {
        //        //返回前台对应的格式
        HashMap<String, Object> map = new HashMap<>();
        //TUser user = userService.getCurrentUser("wangdong");//写死的,要从ticket中查出来
        if (!StringUtils.isEmpty(ticket)){
            HashMap<String,Object> muser =  userService.getCurrentUserByTicket(ticket);

            String geographic ="{\"geographic\":{\"province\":{\"label\":\"浙江省\",\"key\":\"330000\"},\"city\":{\"label\":\"杭州市\",\"key\":\"330100\"}}}";
            HashMap vgeographic = new ObjectMapper().readValue(geographic, new HashMap<>().getClass());
            String tags = "[{\"key\":\"0\",\"label\":\"很有想法的\"},{\"key\":\"1\",\"label\":\"专注设计\"},{\"key\":\"2\",\"label\":\"辣~\"}]";
            ArrayList ltags = new ObjectMapper().readValue(tags, new ArrayList<>().getClass());

            if (muser.containsKey("user")){
                TUser user = (TUser) muser.get("user");

                map.put("name",user.getUsername());
                map.put("avatar",user.getHeadUrl());
                map.put("userid",user.getUid());
                map.put("email",user.getEmail());
                map.put("signature",user.getSignature());
                map.put("title",user.getTitle());
                map.put("group",user.getGroup());
                map.put("notifyCount",12); //可以查其他表得到
                map.put("unreadCount",11);
                map.put("country",user.getCountry());
                map.put("address",user.getAddress());
                map.put("phone",user.getMobile());

                map.put("tags",ltags);
                map.put("geographic",vgeographic.get("geographic"));
                return map;
            }else {

                response.setStatus(401);
                return map;
            }



        }

        map.put("status",401);
        return map;
    }

    @GetMapping("/currentUser2")
    public Map<String,Object> getCurrentUserforHostholder(HttpServletResponse response){
        HashMap<String, Object> users = hostholder.getUsers();
        if (users!=null){
            return users;
        }else {
            response.setStatus(401);
            return null;
        }
    }

}
