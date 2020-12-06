package com.natsumes.wezard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.vo.AchievementVo;
import com.natsumes.wezard.entity.vo.ProfitDetailVo;
import com.natsumes.wezard.entity.vo.ProfitVo;
import com.natsumes.wezard.enums.ProfitStatusEnum;
import com.natsumes.wezard.enums.ResponseEnum;
import com.natsumes.wezard.mapper.AchievementMapper;
import com.natsumes.wezard.pojo.Achievement;
import com.natsumes.wezard.service.AchievementService;
import com.natsumes.wezard.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Slf4j
@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public Response<AchievementVo> list(Integer uid) {
        AchievementVo achievementVo = new AchievementVo();
        List<Achievement> achievements = achievementMapper.selectByUid(uid);
        achievements.forEach(achievement -> {
            if (DateUtils.isThisMonth(achievement.getStartTime())) {
                achievementVo.updateProfit(achievement.getProfit(), achievement.getAchievement(),
                        achievement.getSelfAchievement(), achievement.getSubAchievement());
            }
            if (DateUtils.isPointMonth(achievement.getStartTime(), -1)) {
                achievementVo.updateLastProfit(achievement.getProfit(), achievement.getAchievement(),
                        achievement.getSelfAchievement(), achievement.getSubAchievement());
            }
        });
        achievementVo.setUserId(uid);

        return Response.success(achievementVo);
    }

    @Override
    public Response<ProfitVo> profit(Integer uId) {
        ProfitVo profitVo = new ProfitVo();
        List<Achievement> achievements = achievementMapper.selectByUid(uId);
        BigDecimal profit = BigDecimal.ZERO;
        BigDecimal validProfit = BigDecimal.ZERO;
        /**
         * 每个月7日之后可以领取上个月的利润
         */
        for (Achievement achievement : achievements) {
            if (DateUtils.isPointMonth(achievement.getStartTime(), -1)) {
                profitVo.setLastProfit(achievement.getProfit());
                if (!DateUtils.isBeforeDay(7)
                        && ProfitStatusEnum.VALID_PAID.getCode() >= achievement.getStatus()) {
                    validProfit = validProfit.add(achievement.getProfit());
                }
            }
            if (DateUtils.isBeforeMonth(achievement.getStartTime(), -1)
                    && ProfitStatusEnum.VALID_PAID.getCode() >= achievement.getStatus()) {
                validProfit = validProfit.add(achievement.getProfit());
            }
            profit = profit.add(achievement.getProfit());
        }
        profitVo.setTotalProfit(profit);
        profitVo.setValidProfit(validProfit);
        profitVo.setUserId(uId);
        return Response.success(profitVo);
    }

    /**
     * status: 0-未领取，5-可领取，10-领取中，15-已领取
     *
     * @param uId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Response<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize) {
        //ProductDetailVo productDetailVo = new ProductDetailVo();
        PageHelper.startPage(pageNum, pageSize);
        List<Achievement> achievements = achievementMapper.selectByUid(uId);
        PageInfo pageInfo = new PageInfo<>(achievements);

        List<ProfitDetailVo> profitDetailVos = achievements.stream()
                .map(this::achievement2ProfitDetailVo)
                .collect(Collectors.toList());

        pageInfo.setList(profitDetailVos);

        return Response.success(pageInfo);
    }

    @Override
    public Response updateProfit(Integer userId, Integer profitId, BigDecimal number) {
        //todo: 钱数目校验
        Achievement achievementDb = achievementMapper.selectByPrimaryKey(profitId);

        if (ProfitStatusEnum.VALID_PAID.getCode() < achievementDb.getStatus()) {
            return Response.error(ResponseEnum.PROFIT_CANT_RECEIVE);
        }

        if (DateUtils.isBeforeDay(7) && DateUtils.isPointMonth(achievementDb.getStartTime(), -1)) {
            return Response.error(ResponseEnum.PROFIT_CANT_RECEIVE);
        }

        if (achievementDb.getProfit().doubleValue() != number.doubleValue()) {
            return Response.error(ResponseEnum.PROFIT_NUMBER_ERROR);
        }

        Achievement achievement = new Achievement();
        achievement.setId(profitId);
        achievement.setUserId(userId);
        achievement.setStatus(ProfitStatusEnum.PAYING.getCode());
        int i = achievementMapper.updateByPrimaryKeySelective(achievement);
        if (i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR);
        }
        return Response.success("领取成功，三个自然日内客服会转账给您，请关注");
    }

    @Override
    public Response updateProfit(Integer userId, BigDecimal number) {
        List<Achievement> achievements = achievementMapper.selectByUid(userId);
        BigDecimal validProfit = BigDecimal.ZERO;
        for (Achievement achievement : achievements) {
            if (DateUtils.isPointMonth(achievement.getStartTime(), -1)) {
                if (!DateUtils.isBeforeDay(7)
                        && ProfitStatusEnum.VALID_PAID.getCode() >= achievement.getStatus()) {
                    validProfit = validProfit.add(achievement.getProfit());
                    achievement.setStatus(ProfitStatusEnum.PAYING.getCode());
                }
            }
            if (DateUtils.isBeforeMonth(achievement.getStartTime(), -1)
                    && ProfitStatusEnum.VALID_PAID.getCode() >= achievement.getStatus()) {
                validProfit = validProfit.add(achievement.getProfit());
                achievement.setStatus(ProfitStatusEnum.PAYING.getCode());
            }
        }
        if (validProfit.doubleValue() != number.doubleValue()) {
            return Response.error(ResponseEnum.PROFIT_NUMBER_ERROR);
        }
        int i = achievementMapper.updateBatchSelective(achievements);
        if (!BigDecimal.ZERO.equals(number) && i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR);
        }
        return Response.success("领取成功，三个自然日内客服会转账给您，请关注");
    }

    private ProfitDetailVo achievement2ProfitDetailVo(Achievement achievement) {
        ProfitDetailVo profitDetailVo = new ProfitDetailVo();
        BeanUtils.copyProperties(achievement, profitDetailVo);

        /**
         * 0 - 不可领取，5 - 可领取， 10 - 领取中， 15 - 已领取
         */
        if (ProfitStatusEnum.UNPAID.getCode().equals(achievement.getStatus())
                // 本月之前 // 本月之前
                && DateUtils.isBeforeMonth(achievement.getStartTime(), -1)
                // 当月7号之后
                && DateUtils.isAfterDay(7)) {
            profitDetailVo.setStatus(ProfitStatusEnum.VALID_PAID.getCode());
        }

        return profitDetailVo;
    }

}
