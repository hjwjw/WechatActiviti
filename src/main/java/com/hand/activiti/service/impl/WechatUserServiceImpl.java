package com.hand.activiti.service.impl;

import com.hand.activiti.dto.WechatUser;
import com.hand.activiti.mapper.WechatUserMapper;
import com.hand.activiti.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HJW on 2017/7/29 0029.
 */
@Service
public class WechatUserServiceImpl implements IWechatUserService {

    @Autowired
    WechatUserMapper wechatUserMapper;

    @Override
    public List<WechatUser> getWechatUserList() {
        return wechatUserMapper.getWechatUserList();
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return wechatUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WechatUser record) {
        return wechatUserMapper.insert(record);
    }

    @Override
    public int insertSelective(WechatUser record) {
        return wechatUserMapper.insertSelective(record);
    }

    @Override
    public WechatUser selectByPrimaryKey(Integer id) {
        return wechatUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WechatUser record) {
        return wechatUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WechatUser record) {
        return wechatUserMapper.updateByPrimaryKey(record);
    }
}
