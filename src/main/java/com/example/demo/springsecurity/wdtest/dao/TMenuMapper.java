package com.example.demo.springsecurity.wdtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.springsecurity.wdtest.model.TMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TMenuMapper extends BaseMapper<TMenu> {
    int deleteByPrimaryKey(Integer id);

    //int insert(TMenu record);

    TMenu selectByPrimaryKey(Integer id);

    List<TMenu> selectAll();

    int updateByPrimaryKey(TMenu record);

    List<TMenu> selectParenMebu();

    List<TMenu> selectChildrenMenuByParentId(@Param("id") int parentId);

    List<TMenu> selectMenuListByRoleId(@Param("roleId") int roleId);


}