package com.hand.activiti.mapper;

import com.hand.activiti.dto.WechatUser;

import java.util.List;

public interface WechatUserMapper {
    List<WechatUser> getWechatUserList();

    int deleteByPrimaryKey(Integer id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);
}