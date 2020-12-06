package com.natsumes.wezard.mapper;

import com.natsumes.wezard.pojo.Comment;

import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectSelective(Comment comment);
}