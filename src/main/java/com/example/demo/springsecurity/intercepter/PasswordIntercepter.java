package com.example.demo.springsecurity.intercepter;

import com.example.demo.springsecurity.wdtest.model.Hostholder;
import com.example.demo.springsecurity.wdtest.model.TUser;
import com.example.demo.springsecurity.wdtest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
@Component
public class PasswordIntercepter implements HandlerInterceptor {
    public static final Logger log = LoggerFactory.getLogger(PasswordIntercepter.class);
    @Autowired
    private UserService userService;
    @Autowired
    private Hostholder hostholder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(ticket)){
            HashMap<String, Object> map = new HashMap<>(); //返回user 的结构
            HashMap<String,Object> muser =  userService.getCurrentUserByTicket(ticket);

            String geographic ="{\"geographic\":{\"province\":{\"label\":\"浙江省\",\"key\":\"330000\"},\"city\":{\"label\":\"杭州市\",\"key\":\"330100\"}}}";
            HashMap vgeographic = new ObjectMapper().readValue(geographic, new HashMap<>().getClass());
            String tags = "[{\"key\":\"0\",\"label\":\"很有想法的\"},{\"key\":\"1\",\"label\":\"专注设计\"},{\"key\":\"2\",\"label\":\"辣~\"}]";
            ArrayList ltags = new ObjectMapper().readValue(tags, new ArrayList<>().getClass());

            if (muser.containsKey("user")) {
                TUser user = (TUser) muser.get("user");

                map.put("name", user.getUsername());
                map.put("avatar", user.getHeadUrl());
                map.put("userid", user.getUid());
                map.put("email", user.getEmail());
                map.put("signature", user.getSignature());
                map.put("title", user.getTitle());
                map.put("group", user.getGroup());
                map.put("notifyCount", 12); //可以查其他表得到
                map.put("unreadCount", 11);
                map.put("country", user.getCountry());
                map.put("address", user.getAddress());
                map.put("phone", user.getMobile());

                map.put("tags", ltags);
                map.put("geographic", vgeographic.get("geographic"));
                // 将map信息存入 threadLocal
                hostholder.setUsers(map);
            }else {
                log.info("被拦截了,ticket无效");
                return true; //401
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostholder.clear();
    }
}
