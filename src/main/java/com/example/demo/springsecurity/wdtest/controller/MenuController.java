package com.example.demo.springsecurity.wdtest.controller;

import com.example.demo.springsecurity.wdtest.model.Hostholder;
import com.example.demo.springsecurity.wdtest.model.TMenu;
import com.example.demo.springsecurity.wdtest.service.MenuService;
import com.example.demo.springsecurity.wdtest.service.RoleService;
import com.example.demo.springsecurity.wdtest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MenuController {
    public static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Hostholder hostholder;

    @Autowired
    private UserService userService;

    @GetMapping("/menu")  //在递归中调服务,已没使用
    public List<Object> getMenu(){
        Date starttime = new Date();
        List<Object> menu = menuService.getMenu();
        Date endtime = new Date();
        log.info(""+(endtime.getTime()-starttime.getTime()));
        return  menu;
    }

    @GetMapping("/menu/{type}")
    public List<Object> getMenu1(@PathVariable(value = "type") String type){
        //Date starttime = new Date();
        ArrayList<Integer> userids = null;
        //首次登陆时根据用户名查出用户,再查出角色ID,首次登陆时hostholder并没有信息
        if(hostholder.getUsers()==null){
            log.info("退出时调用了");
            return null;
        }else {
            userids = roleService.getRoleIdByUserId(Long.parseLong(hostholder.getUsers().get("userid").toString()));//user类还是要好好设计下
        }


        List<TMenu> menuList = null;

        if ("children".equals(type)){
            menuList = menuService.gettMenus(); //菜单管理,需要查询全部
        }else if(userids!=null && userids.size()>0){
            //首次登陆用第一个角色,查出该角色菜单
            menuList = menuService.getMenusByRoleId(userids.get(0));
        }else {
            log.info("该用户没有赋予角色权限");
        }

        List<Object> list = null;
        //List<TMenu> menus = menuService.gettMenus();
        if (menuList!=null && menuList.size()>0){
             list = menuService.getMenus(menuList,type);
        }

        //Date endtime = new Date();
        //log.info(""+(endtime.getTime()-starttime.getTime()));
        return  list;
    }

    @PostMapping("/menu/{method}")
    @Transactional
    public List<Object> addOrUpdateMenu(@RequestParam String name,
                                @RequestParam String path,
                                @RequestParam String icon,
                                @RequestParam(value = "key",defaultValue = "0") Integer key,
                                @RequestParam(value = "parentId",required = false) Integer parentId,
                                @PathVariable("method") String method){
        TMenu menu = new TMenu();
        menu.setName(name);
        menu.setPath(path);
        menu.setIcon(icon);
        if ("add".equals(method)){
            menu.setParentId(key);
        } else {
            menu.setMenuId(key);
            menu.setParentId(parentId);
        }

        List<Object> list = menuService.addOrUpdateMenu(menu,method);
        return list;
    }

    @DeleteMapping(value = "/menu/delete")
    public List<Object> deleteMenu(@RequestParam("keys") Integer key) {
        List<Object> list = menuService.deleteMenu(key);
        return list;
    }
}
