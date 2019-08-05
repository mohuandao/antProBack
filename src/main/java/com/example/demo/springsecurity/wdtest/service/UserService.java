package com.example.demo.springsecurity.wdtest.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.springsecurity.wdtest.dao.TLoginTicketMapper;
import com.example.demo.springsecurity.wdtest.dao.TUserMapper;
import com.example.demo.springsecurity.wdtest.model.TLoginTicket;
import com.example.demo.springsecurity.wdtest.model.TUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.*;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private TLoginTicketMapper tLoginTicketMapper;

    public int insert(TUser record) {
        return userMapper.insert(record);
    }

    public List<TUser> selectAll(){
        return  userMapper.selectAll();
    }

    /** 
    * @Description: 注册功能,还不完善,没和前台对接 
    * @Param: [username, password] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    @Transactional
    public Map<String,Object> register(String username,String password){
        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map ;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map ;
        }
        TUser user = new TUser();
        user.setUsername(username);
        user = userMapper.selectOne(new QueryWrapper<TUser>(user));
        if (user != null){
            map.put("msg","用户名已存在");
            return map ;
        }
        //注册成功,完成自动登录,写入ticket
        TUser tUser = new TUser();
        tUser.setUsername(username);
        tUser.setSalt(UUID.randomUUID().toString().substring(0,5));
        password = DigestUtils.md5DigestAsHex((password+tUser.getSalt()).getBytes());
        tUser.setPassword(password);
        userMapper.insert(tUser);


        String ticket = addLoginTicket(tUser.getUid());
        map.put("ticket",ticket);
        return map;
    }

    /** 
    * @Description: 登陆功能
    * @Param: [username, password] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    @Transactional
    public Map<String,Object> login(String username,String password){
        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map ;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map ;
        }

       TUser user = userMapper.getUserByName(username);
        if (user == null){
            map.put("msg","用户名不存在");
            return map ;
        }

        if (! user.getPassword().equals(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()))){
            map.put("msg","密码错误");
            return map;

        }
        //登陆成功
        String ticket = addLoginTicket(user.getUid());
        map.put("ticket",ticket);
        map.put("user",user);
        return map;

    }

//    public Map<String,Object> logout(String ticket){
//        tLoginTicketMapper.selectOne(new QueryWrapper<>());
//
//    }


    /*public TUser getCurrentUser(String ticket){
        return userMapper.getCurrentUserByTicket(ticket);
    }*/


    public TUser getCurrentUser(String username){
        return userMapper.getUserByName(username);
    }




    /** 
    * @Description: 根据ticket获取当前用户信息
    * @Param: [ticket] 
    * @return: java.util.HashMap<java.lang.String,java.lang.Object> 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    @Transactional
    @Cacheable(value = "Mycache",key = "targetClass + methodName +#ticket")
    public HashMap<String,Object> getCurrentUserByTicket(String ticket){
        HashMap<String, Object> map = new HashMap<>();

        TLoginTicket tLoginTicketByTicket = tLoginTicketMapper.getTLoginTicketByTicket(ticket);
        if (tLoginTicketByTicket!=null && "0".equals(tLoginTicketByTicket.getStatus()) &&
                tLoginTicketByTicket.getExpired().getTime() > new Date().getTime()){ //状态为0,时间未过期

            TUser user = userMapper.selectByPrimaryKey(tLoginTicketByTicket.getUid());
            map.put("user",user);
            return map;
        }else {
            log.error("ticket无效");  //要返回让用户重新登录
            //map.put("redirect","/login");
            map.put("status",401);
            return map;
        }


    }

    /** 
    * @Description: 生成ticket,注册成功后和每次登陆成功生成 
    * @Param: [uid] 
    * @return: java.lang.String 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    private String addLoginTicket(Long uid){
        TLoginTicket loginTicket = new TLoginTicket();
        loginTicket.setUid(uid);
        loginTicket.setStatus("0");//有效
        Date date = new Date();
        date.setTime(date.getTime()+24*3600*1000);//一天后
        loginTicket.setExpired(date);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        tLoginTicketMapper.insert(loginTicket);
        return loginTicket.getTicket();
    }
}

