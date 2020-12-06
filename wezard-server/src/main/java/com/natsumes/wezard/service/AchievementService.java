package com.natsumes.wezard.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.AchievementForm;
import com.natsumes.wezard.entity.vo.AchievementVo;
import com.natsumes.wezard.entity.vo.ProfitVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Service
public interface AchievementService {

    /**
     * 用户业绩查询
     *
     * @param uId
     * @return
     */
    Response<AchievementVo> list(Integer uId);

    Response<ProfitVo> profit(Integer uId);

    Response<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize);

    Response updateProfit(Integer userId, Integer profitId, BigDecimal number);

    Response updateProfit(Integer userId, BigDecimal number);
}
