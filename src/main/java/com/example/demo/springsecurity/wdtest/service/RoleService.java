package com.example.demo.springsecurity.wdtest.service;

import com.example.demo.springsecurity.wdtest.dao.TRoleMapper;
import com.example.demo.springsecurity.wdtest.dao.TUserRoleRelMapper;
import com.example.demo.springsecurity.wdtest.model.TUserRoleRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-security
 * @description: ${description}
 * @author: wdong
 * @date 2019/8/2 9:46
 **/
@Service
public class RoleService {
    @Autowired
    private TRoleMapper roleMapper;

    @Autowired
    private TUserRoleRelMapper userRoleRelMapper;
    
    /** 
    * @Description: 根据用户ID查出所有角色ID 
    * @Param: [userId] 
    * @return: java.util.ArrayList<java.lang.Integer> 
    * @Author: wdong 
    * @Date: 2019/8/2 
    */ 
    public ArrayList<Integer> getRoleIdByUserId(Long userId){
        List<TUserRoleRel> userRoleRels = userRoleRelMapper.selectByUserId(userId);
        ArrayList<Integer> list = new ArrayList<>();
        userRoleRels.forEach(userRoleRel ->{
            list.add(userRoleRel.getRoleId());
        });
        return list;
    }
    
    /** 
    * @Description: 删掉用户的所有角色
    * @Param: [userId] 
    * @return: int 
    * @Author: wdong 
    * @Date: 2019/8/5 
    */ 
    public int deleteRoleIdByUserId(Long userId){
       return userRoleRelMapper.deleteByUserId(userId);
    }
    
    /** 
    * @Description: 为用户添加多个角色 ,产生多条insert语句,效率不高
    * @Param: [userId, roleIds]
    * @return: void 
    * @Author: wdong 
    * @Date: 2019/8/5 
    */ 
    public void addRoleIdByUserId_bak(Long userId ,List<Integer> roleIds){
        if (userId != null && roleIds != null ){
            roleIds.forEach((roleId) ->{
                userRoleRelMapper.insertRoleIdstoUser(userId,roleId);
            });
        }
    }

    /** 
    * @Description: 为用户添加多个角色,批量插入,执行一条SQL
    * @Param: [userId, roleIds] 
    * @return: void 
    * @Author: wdong 
    * @Date: 2019/8/7 
    */ 
    public void addRoleIdByUserId(Long userId ,List<Integer> roleIds){
        if (userId != null && roleIds != null ){
            userRoleRelMapper.insertRoleIdstoUserBatch(userId,roleIds);
        }
    }

}
