package com.natsumes.mapper;

import com.natsumes.entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countById(Integer id);

    int countByUsername(String username);

    int countByOpenid(String openid);

    int countByEmail(String email);

    User selectByUsername(String username);

    User selectByOpenid(String openid);

    List<User> selectByParentId(Integer parentId);

    List<User> selectAll();
}