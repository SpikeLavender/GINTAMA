package com.natsume.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsume.entity.Achievement;
import com.natsume.entity.Order;
import com.natsume.entity.User;
import com.natsume.enums.AchievementTypeEnum;
import com.natsume.enums.LevelEnum;
import com.natsume.enums.OrderStatusEnum;
import com.natsume.enums.ProfitStatusEnum;
import com.natsume.mapper.AchievementMapper;
import com.natsume.mapper.OrderMapper;
import com.natsume.mapper.UserMapper;
import com.natsume.service.AchievementService;
import com.natsume.utils.DateUtils;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.natsume.consts.NatsumeConst.ROOT_USER_PARENT_ID;

@Slf4j
@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AchievementMapper achievementMapper;

    @Override
    public ResponseVo<AchievementVo> list(Integer uid) {
        AchievementVo achievementVo = new AchievementVo();
        List<Achievement> achievements = achievementMapper.selectByUid(uid);
        achievements.forEach(achievement -> {
            if (DateUtils.isThisWeek(achievement.getStartTime())) {
                achievementVo.updateProfit(achievement.getProfit(), achievement.getAchievement(),
                        achievement.getSelfAchievement(), achievement.getSubAchievement());
            }
            if (DateUtils.isPointWeek(achievement.getStartTime(), -1)) {
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
        for (Achievement achievement : achievements) {
            if (DateUtils.isPointWeek(achievement.getStartTime(), -1)) {
                profitVo.setLastProfit(achievement.getProfit());
            }
            if (DateUtils.isBeforeWeek(achievement.getStartTime(), -2)
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

    @Override
    public ResponseVo<PageInfo> detail(Integer uId, Integer pageNum, Integer pageSize) {
        //ProductDetailVo productDetailVo = new ProductDetailVo();
        PageHelper.startPage(pageNum, pageSize);
        List<Achievement> achievements = achievementMapper.selectByUid(uId);

        List<ProfitDetailVo> profitDetailVos = achievements.stream()
                .map(this::achievement2ProfitDetailVo)
                .collect(Collectors.toList());

        PageInfo<ProfitDetailVo> pageInfo = new PageInfo<>(profitDetailVos);

        return ResponseVo.success(pageInfo);
    }


    private ProfitDetailVo achievement2ProfitDetailVo(Achievement achievement) {
        ProfitDetailVo profitDetailVo = new ProfitDetailVo();
        BeanUtils.copyProperties(achievement, profitDetailVo);
        return profitDetailVo;
    }

    /**
     * 创建本周业绩条目
     */
    public void updateAchievementInDB(List<Achievement> achievements) {
        int i = achievementMapper.insertBatch(achievements);
    }

    public List<Achievement> createWeekAchievement() {
        Predicate<Order> predicate = order -> order.getStatus().equals(OrderStatusEnum.TRADE_SUCCESS.getCode())
                && DateUtils.isThisWeek(order.getEndTime());
        return createAchievement(predicate, AchievementTypeEnum.WEEK_ACHIEVEMENT.getCode(), 0);
    }

    /**
     * 指定week周前的订单
     * @param week 1：一周前
     */
    public List<Achievement> createWeekAchievement(Integer week) {
        Predicate<Order> predicate = order -> order.getStatus().equals(OrderStatusEnum.TRADE_SUCCESS.getCode())
                && DateUtils.isPointWeek(order.getEndTime(), week * -1);
        return createAchievement(predicate, AchievementTypeEnum.WEEK_ACHIEVEMENT.getCode(), week);
    }

    private void calcAchievementByUser(List<IDVo> idVos, Map<Integer, Achievement> achievementMap,
                                       Integer parentId, Integer type) {
        //Map<Integer, Order> collect = orders.stream().collect(Collectors.toMap(Order::getUserId, Order::getStatus));

        for (IDVo idVo : idVos) {
            if (idVo.getParentId().equals(parentId)) {
                calcAchievementByUser(idVo.getSubIDVos(), achievementMap, idVo.getId(), type);
            }

            Achievement achievement = achievementMap.get(idVo.getId());
            if (achievement == null) {
                achievement = new Achievement();
            }

            List<IDVo> subIDVos = idVo.getSubIDVos();
            BigDecimal achievementValue = BigDecimal.ZERO;
            BigDecimal subProfit = BigDecimal.ZERO;
            for (IDVo subIDVo : subIDVos) {
                BigDecimal subAchievementValue =
                        subIDVo.getAchievement() == null ? BigDecimal.ZERO : subIDVo.getAchievement().getAchievement();
                achievementValue = achievementValue.add(subAchievementValue);
                BigDecimal ratio = subIDVo.getAchievement() == null ? BigDecimal.ZERO : LevelEnum.getRatio(subAchievementValue);
                subProfit = subProfit.add(subAchievementValue.multiply(ratio));
            }
            achievement.setSubAchievement(achievementValue);
            achievement.setUserId(idVo.getId());
            achievement.setParentId(idVo.getParentId());
            BigDecimal totalAchievementValue = achievementValue.add(achievement.getSelfAchievement());
            achievement.setAchievement(totalAchievementValue);
            achievement.setLevel(LevelEnum.getLevel(totalAchievementValue));
            BigDecimal profit = totalAchievementValue.multiply(LevelEnum.getRatio(totalAchievementValue)).subtract(subProfit);
            achievement.setProfit(profit);
            idVo.setAchievement(achievement);
            achievementMap.put(idVo.getId(), achievement);
        }
    }

    //todo: 更新问题
    private List<Achievement> createAchievement(Predicate<Order> predicate, Integer type, Integer week) {
        List<User> users = getUsers();
        List<IDVo> idVos = createMap(users);
        log.info("map={}", JSONUtils.printFormat(idVos));

        List<Order> allOrders = getAllOrders(users, predicate);
        Map<Integer, Achievement> profitMap = calcAchievement(allOrders, week);

        calcAchievementByUser(idVos, profitMap, ROOT_USER_PARENT_ID, type);

        List<Achievement> profits = profitMap.values().stream().peek(e -> {
            e.setEndTime(DateUtils.getEndDayOfWeekBefore(week));
            e.setStartTime(DateUtils.getStartDayOfWeekBefore(week));
        }).collect(Collectors.toList());

        log.info("profitMap=\n{}", JSONUtils.printFormat(profits));
        log.info("idMap=\n{}", JSONUtils.printFormat(idVos));
        return profits;
    }

    /**
     * 计算总业绩
     * @param orders
     * @return
     */
    private Map<Integer, Achievement> calcAchievement(List<Order> orders, Integer week) {
        //计算总业绩
        Map<Integer, List<Order>> ordersMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getUserId));
        Map<Integer, Achievement> profitMap = new HashMap<>();
        ordersMap.forEach((id, orderVos) -> {
            Achievement achievement = new Achievement();

            BigDecimal payment = orderVos.stream()
                    .map(Order::getPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            achievement.setSelfAchievement(payment);
            achievement.setUserId(id);
            achievement.setSubAchievement(BigDecimal.ZERO);

//            Optional<Order> max = orderVos.stream().max((a, b) -> {
//                if (a.getEndTime().getTime() >= b.getEndTime().getTime()) {
//                    return 1;
//                }
//                return -1;
//            });
//            Date date = new Date();
//            if (max.isPresent()) {
//                date = max.get().getEndTime();
//            }
//            achievement.setWeek(DateUtils.getWeekByTime(new Date()) - week);
//            achievement.setEndTime(date);
            profitMap.put(id, achievement);
        });



        return profitMap;
    }


    private List<User> getUsers() {
        return userMapper.selectAll();
    }

    private List<Order> getAllOrders(List<User> users, Predicate<Order> predicate) {
        //uid -> parentId
        //List<IDVo> idVos = new ArrayList<>();
        Set<Integer> ids = users.stream().map(User::getId).collect(Collectors.toSet());

        List<Order> orders = orderMapper.selectByUidSet(ids);

        return orders.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private List<IDVo> createMap(List<User> users) {
        List<IDVo> resIdVo = users.stream()
                .filter(e -> e.getParentId().equals(ROOT_USER_PARENT_ID))
                .map(this::user2IDVo)
                .collect(Collectors.toList());

        findSubId(resIdVo, users);
        return resIdVo;
    }

    private void findSubId(List<IDVo> resIdVos, List<User> users) {
        for (IDVo resIdVo : resIdVos) {
            List<IDVo> subResIDs = new ArrayList<>();
            for (User user : users) {
                IDVo idVo = new IDVo();
                if (resIdVo.getId().equals(user.getParentId())) {
                    BeanUtils.copyProperties(user, idVo);
                    subResIDs.add(idVo);
                }
                resIdVo.setSubIDVos(subResIDs);
                findSubId(subResIDs, users);
            }
        }

    }

    private IDVo user2IDVo(User user) {
        IDVo idVo = new IDVo();
        BeanUtils.copyProperties(user, idVo);
        return idVo;
    }


}
