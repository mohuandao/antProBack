package com.example.demo.springsecurity.wdtest.service;

import com.example.demo.springsecurity.wdtest.dao.TMenuMapper;
import com.example.demo.springsecurity.wdtest.model.TMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class MenuService {
    @Autowired
    private TMenuMapper menuMapper;

    @Autowired
    private RoleService roleService;

    /**
     * 递归里面含有查询,完成这个请求会多次查询数据库
     * @return
     */
    @Transactional
    public List<Object> getMenu(){

        ArrayList<Object> list = new ArrayList<>();
        List<TMenu> rootmenus = menuMapper.selectParenMebu();
        for(TMenu menu:rootmenus){
            HashMap<String, Object> map = new HashMap<>();
            map.put("path",menu.getPath());
            map.put("component",menu.getComponent());
            map.put("routes",getChildrenMenu(menu.getMenuId()));
            list.add(map);
        }

        return list;
    }

    public List<Object> getChildrenMenu(int parentId){
        ArrayList<Object> list = new ArrayList<>();

        List<TMenu> childMenus = menuMapper.selectChildrenMenuByParentId(parentId);
        for (TMenu childMenu:childMenus){
            HashMap<String, Object> map = new HashMap<>();
            map.put("path",childMenu.getPath());
            map.put("component",childMenu.getComponent());
            map.put("name",childMenu.getName());
            map.put("icon",childMenu.getIcon());
            map.put("redirect",childMenu.getRedirect());
            map.put("routes",getChildrenMenu(childMenu.getMenuId()));
            list.add(map);
        }

        return list;
    }



    /** 
    * @Description: 后台一次性查出所有菜单,然后处理这些数据 
    * @Param: [tMenus, type] 
    * @return: java.util.List<java.lang.Object> 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    public List<Object> getMenus(List<TMenu> tMenus,String type){

        ArrayList<Object> list = new ArrayList<>();
        tMenus.forEach(menu->{
            if (menu.getParentId()==0){
                HashMap<Object, Object> map = new HashMap<>();
                map.put("path",menu.getPath());
                map.put("key",menu.getMenuId()); //id
                map.put("parentId",menu.getParentId());
                if (menu.getComponent()!=null){
                    map.put("component",menu.getComponent());
                }
                if (menu.getRedirect()!=null){
                    map.put("redirect",menu.getRedirect());
                }
                if (menu.getIcon()!=null){
                    map.put("icon",menu.getIcon());
                }
                if (menu.getName()!=null){
                    map.put("name",menu.getName());
                }
                if (getChildrenMenus(tMenus,menu.getMenuId(),type).size()>0){
                    map.put(type,getChildrenMenus(tMenus,menu.getMenuId(),type));
                }


                list.add(map);
            }
        });

        return list;
    }

    /** 
    * @Description: 递归获取子菜单 
    * @Param: [tMenus, id, type] 
    * @return: java.util.List<java.lang.Object> 
    * @Author: wdong 
    * @Date: 2019/7/31 
    */ 
    public List<Object> getChildrenMenus(List<TMenu> tMenus,int id,String type){
        ArrayList<Object> list = new ArrayList<>();
        tMenus.forEach(menu -> {
            if (menu.getParentId().equals(id)){//这就是子菜单
                HashMap<Object, Object> map = new HashMap<>();
                map.put("path",menu.getPath());
                map.put("key",menu.getMenuId()); //id
                map.put("parentId",menu.getParentId());
                if (menu.getComponent()!=null){
                    map.put("component",menu.getComponent());
                }
                if (menu.getName()!=null){
                    map.put("name",menu.getName());
                }
                if (menu.getIcon()!=null){
                    map.put("icon",menu.getIcon());
                }
                if (menu.getRedirect()!=null){
                    map.put("redirect",menu.getRedirect());
                }

                if (getChildrenMenus(tMenus,menu.getMenuId(),type).size()>0){
                    map.put(type,getChildrenMenus(tMenus,menu.getMenuId(),type));
                }

                list.add(map);
            }
        });


        return list;
    }


    public List<TMenu> gettMenus() {
        return menuMapper.selectAll();
    }
    
    /** 
    * @Description: 根据角色id查出菜单列表 ,用户切换角色可显示不同菜单
    * @Param: [roleid] 
    * @return: java.util.List<com.example.demo.springsecurity.wdtest.model.TMenu> 
    * @Author: wdong 
    * @Date: 2019/8/2 
    */ 
    public List<TMenu> getMenusByRoleId(int roleid){
        List<TMenu> menuList = menuMapper.selectMenuListByRoleId(roleid);

        return menuList;
    }



    /**
     * 新增或修改
     * @param menu
     * @param method
     * @return
     */
    @Transactional
    public List<Object> addOrUpdateMenu(TMenu menu,String method){
        if("add".equals(method)){
            menuMapper.insert(menu);
        }
        if ("update".equals(method)){
            menuMapper.updateByPrimaryKey(menu);
        }

        List<TMenu> tMenus =gettMenus();
        List<Object> menus = getMenus(tMenus,"children");
        return menus;
    }

    //删除
    public List<Object> deleteMenu(Integer key){
        menuMapper.deleteByPrimaryKey(key);
        List<TMenu> tMenus =gettMenus();
        List<Object> menus = getMenus(tMenus,"children");
        return menus;
    }

}
