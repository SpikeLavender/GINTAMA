package com.natsumes.wezard.mapper;

import com.natsumes.wezard.pojo.Achievement;

import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
public interface AchievementMapper {

    /**
     * deleteByPrimaryKey
     * @param id    id
     * @return  int
     */
    int deleteByPrimaryKey(Integer id);

    int insert(Achievement achievement);

    int insertSelective(Achievement achievement);

    Achievement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Achievement achievement);

    int updateByPrimaryKey(Achievement achievement);

    int updateBatchSelective(List<Achievement> achievements);

    int insertBatch(List<Achievement> achievements);

    int replaceBatch(List<Achievement> achievements);

    List<Achievement> selectByUid(Integer id);
}