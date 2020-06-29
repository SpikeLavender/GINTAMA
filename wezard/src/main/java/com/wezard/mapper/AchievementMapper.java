package com.wezard.mapper;

import com.wezard.entity.Achievement;

import java.util.List;

public interface AchievementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Achievement record);

    int insertSelective(Achievement record);

    Achievement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Achievement record);

    int updateByPrimaryKey(Achievement record);

    int updateBatchSelective(List<Achievement> record);

    int insertBatch(List<Achievement> record);

    int replaceBatch(List<Achievement> record);

    List<Achievement> selectByUid(Integer id);
}