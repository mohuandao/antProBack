package com.example.demo.springsecurity.wdtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.springsecurity.wdtest.model.TLoginTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TLoginTicketMapper extends BaseMapper<TLoginTicket> {
    int deleteByPrimaryKey(Integer ltId);

    //int insert(TLoginTicket record);

    TLoginTicket selectByPrimaryKey(Integer ltId);

    List<TLoginTicket> selectAll();

    int updateByPrimaryKey(TLoginTicket record);

    TLoginTicket getTLoginTicketByTicket(String ticket);

}