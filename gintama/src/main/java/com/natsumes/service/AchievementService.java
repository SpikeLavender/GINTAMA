package com.natsumes.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.vo.AchievementVo;
import com.natsumes.vo.ProfitVo;
import com.natsumes.vo.ResponseVo;

public interface AchievementService {

    /**
     * 用户业绩查询
     * @param uId
     * @return
     */
    ResponseVo<AchievementVo> list(Integer uId);

    ResponseVo<ProfitVo> profit(Integer uId);

    ResponseVo<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize);

}
