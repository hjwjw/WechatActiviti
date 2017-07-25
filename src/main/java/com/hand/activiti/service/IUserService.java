package com.hand.activiti.service;

import com.hand.activiti.dto.User;

/**
 * Created by HJW on 2017/7/23 0023.
 */
public interface IUserService {
    public User selectByName(String username);

    public int deleteByPrimaryKey(Long id);

    public  int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);
}
