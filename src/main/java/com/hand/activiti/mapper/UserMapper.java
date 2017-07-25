package com.hand.activiti.mapper;

import com.hand.activiti.dto.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User selectByName(String username);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}