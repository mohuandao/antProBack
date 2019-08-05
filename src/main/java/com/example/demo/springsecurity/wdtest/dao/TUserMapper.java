package com.example.demo.springsecurity.wdtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.springsecurity.wdtest.model.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TUserMapper extends BaseMapper<TUser> {
    int deleteByPrimaryKey(Long uid);

    //int insert(TUser record);

    TUser selectByPrimaryKey(Long uid);

    List<TUser> selectAll();

    int updateByPrimaryKey(TUser record);

    TUser getCurrentUserByTicket(@Param("ticket") String ticket);

    TUser getUserByName(@Param("username") String username);
}