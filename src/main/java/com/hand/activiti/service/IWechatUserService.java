package com.hand.activiti.service;

import com.hand.activiti.dto.WechatUser;

import java.util.List;

/**
 * Created by HJW on 2017/7/29 0029.
 */
public interface IWechatUserService {

    public List<WechatUser> getWechatUserList();

    public int deleteByPrimaryKey(Integer id);

    public int insert(WechatUser record);

    public int insertSelective(WechatUser record);

    public WechatUser selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(WechatUser record);

    public int updateByPrimaryKey(WechatUser record);
}
