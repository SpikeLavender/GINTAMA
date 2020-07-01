package com.natsumes.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsumes.vo.ResponseVo;
import com.natsumes.entity.Achievement;
import com.natsumes.enums.ProfitStatusEnum;
import com.natsumes.mapper.AchievementMapper;
import com.natsumes.service.AchievementService;
import com.natsumes.utils.DateUtils;
import com.natsumes.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public ResponseVo<AchievementVo> list(Integer uid) {
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

        return ResponseVo.success(achievementVo);
    }

    @Override
    public ResponseVo<ProfitVo> profit(Integer uId) {
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
                        && ProfitStatusEnum.UNPAID.getCode().equals(achievement.getStatus())) {
                    validProfit = validProfit.add(achievement.getProfit());
                }
            }
            if (DateUtils.isBeforeMonth(achievement.getStartTime(), -1)
                    && ProfitStatusEnum.UNPAID.getCode().equals(achievement.getStatus())) {
                validProfit = validProfit.add(achievement.getProfit());
            }
            profit = profit.add(achievement.getProfit());
        }
        profitVo.setTotalProfit(profit);
        profitVo.setValidProfit(validProfit);
        profitVo.setUserId(uId);
        return ResponseVo.success(profitVo);
    }

    /**
     * status: 0-未领取，5-可领取，10-领取中，15-已领取
     * @param uId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseVo<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize) {
        //ProductDetailVo productDetailVo = new ProductDetailVo();
        PageHelper.startPage(pageNum, pageSize);
        List<Achievement> achievements = achievementMapper.selectByUid(uId);
        PageInfo pageInfo = new PageInfo<>(achievements);

        List<ProfitDetailVo> profitDetailVos = achievements.stream()
                .map(this::achievement2ProfitDetailVo)
                .collect(Collectors.toList());

        pageInfo.setList(profitDetailVos);

        return ResponseVo.success(pageInfo);
    }

    private ProfitDetailVo achievement2ProfitDetailVo(Achievement achievement) {
        ProfitDetailVo profitDetailVo = new ProfitDetailVo();
        BeanUtils.copyProperties(achievement, profitDetailVo);

        /**
         * 0 - 不可领取，5 - 可领取， 10 - 领取中， 15 - 已领取
         */
        if (ProfitStatusEnum.UNPAID.getCode().equals(achievement.getStatus())
                && DateUtils.isBeforeMonth(achievement.getStartTime(), -1) // 本月之前
                && DateUtils.isAfterDay(7)) {   // 当月7号之后
            profitDetailVo.setStatus(ProfitStatusEnum.VALID_PAID.getCode());
        }

        return profitDetailVo;
    }

}
