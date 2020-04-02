package com.natsume.service;

import com.github.pagehelper.PageInfo;
import com.natsume.vo.AchievementVo;
import com.natsume.vo.ProfitDetailVo;
import com.natsume.vo.ProfitVo;
import com.natsume.vo.ResponseVo;

public interface AchievementService {

    /**
     * 用户业绩查询
     * @param uId
     * @return
     */
    ResponseVo<AchievementVo> list(Integer uId);

    ResponseVo<ProfitVo> profit(Integer uId);

    ResponseVo<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize);

    void weekAchievement();

    void curWeekAchievement();
}
