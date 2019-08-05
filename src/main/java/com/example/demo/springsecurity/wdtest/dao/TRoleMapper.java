package com.example.demo.springsecurity.wdtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.springsecurity.wdtest.model.TRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TRoleMapper extends BaseMapper<TRole> {
    int deleteByPrimaryKey(Integer roleId);

    //int insert(TRole record);

    TRole selectByPrimaryKey(Integer roleId);

    List<TRole> selectAll();

    int updateByPrimaryKey(TRole record);
}