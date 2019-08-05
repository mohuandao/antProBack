package com.example.demo.springsecurity.wdtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.springsecurity.wdtest.model.TUserRoleRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TUserRoleRelMapper extends BaseMapper<TUserRoleRelMapper> {
    int deleteByPrimaryKey(Long urId);

    //int insert(TUserRoleRel record);

    TUserRoleRel selectByPrimaryKey(Long urId);

    List<TUserRoleRel> selectAll();

    List<TUserRoleRel> selectByUserId(@Param("userId") Long userId);

    //int updateByPrimaryKey(TUserRoleRel record);
}