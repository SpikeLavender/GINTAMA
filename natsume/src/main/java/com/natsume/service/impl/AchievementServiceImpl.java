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
import org.springframework.scheduling.annotation.Scheduled;
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
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AchievementMapper achievementMapper;

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

    @Override
    public void weekAchievement() {
        List<Achievement> achievements = buildWeekAchievement();
        updateAchievementInDB(achievements);
    }

    @Override
    public void curWeekAchievement() {
        List<Achievement> achievements = buildCurWeekAchievement();
        updateAchievementInDB(achievements);
    }

    /**
     * 创建全部周业绩条目
     * 每 fixedRate 执行一次，刷新本周业绩
     * @return List<Achievement>
     */
    private List<Achievement> buildWeekAchievement() {
        List<User> users = getUsers();
        Predicate<Order> predicate = order -> order.getStatus() >= OrderStatusEnum.PAID.getCode();
        List<Order> orders = getOrders(users, predicate);
        Map<Date, List<Order>> ordersMap = splitOrdersByWeek(orders);
        return createAchievement(ordersMap);
    }

    /**
     * 创建本周业绩
     * 每 fixedRate 执行一次，刷新本周业绩，30min
     * @return
     */
    private List<Achievement> buildCurWeekAchievement() {
        List<User> users = getUsers();
        Predicate<Order> predicate = order -> order.getStatus() >= OrderStatusEnum.PAID.getCode()
                && DateUtils.isThisWeek(order.getPaymentTime());
        List<Order> orders = getOrders(users, predicate);
        Map<Date, List<Order>> ordersMap = splitOrdersByWeek(orders);

        return createAchievement(ordersMap);
    }

    /**
     * 按周拆分 {"startTime": [order1, order2]}
     * @param orders
     * @return
     */
    private Map<Date, List<Order>> splitOrdersByWeek(List<Order> orders) {
        Map<Date, List<Order>> ordersWeekMap = new HashMap<>();
        orders.forEach(order -> {
            Date paymentTime = order.getPaymentTime();
            Date startDayOfWeek = DateUtils.getStartDayOfWeek(paymentTime);
            List<Order> weekOrders = new ArrayList<>();
            if (ordersWeekMap.containsKey(startDayOfWeek)) {
                weekOrders = ordersWeekMap.get(startDayOfWeek);
            }
            weekOrders.add(order);
            ordersWeekMap.put(startDayOfWeek, weekOrders);
        });

        return ordersWeekMap;
    }

    /**
     * 创建订单条目
     * @param ordersMap
     * @return
     */
    private List<Achievement> createAchievement(Map<Date, List<Order>> ordersMap) {
        List<User> users = getUsers();
        List<IDVo> idVos = createIDVos(users);
        log.info("map={}", JSONUtils.printFormat(idVos));

        List<Achievement> profits = new ArrayList<>();
        ordersMap.forEach((weekDate, orders) -> {
            Map<Integer, Achievement> profitMap = calcAchievement(orders);
            calcAchievementByUser(idVos, profitMap, ROOT_USER_PARENT_ID);
            List<Achievement> weekProfits = profitMap.values().stream().peek(e -> {
                e.setEndTime(DateUtils.getEndDayOfWeek(weekDate));
                e.setStartTime(weekDate);
            }).collect(Collectors.toList());
            profits.addAll(weekProfits);
        });

        log.info("profitMap=\n{}", JSONUtils.printFormat(profits));
        log.info("idMap=\n{}", JSONUtils.printFormat(idVos));
        return profits;
    }

    /**
     * 计算总业绩
     * @param orders
     * @return
     */
    private Map<Integer, Achievement> calcAchievement(List<Order> orders) {
        //计算总业绩 {"userId": []}

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
            profitMap.put(id, achievement);
        });

        return profitMap;
    }

    private void calcAchievementByUser(List<IDVo> idVos, Map<Integer, Achievement> achievementMap,
                                       Integer parentId) {
        //Map<Integer, Order> collect = orders.stream().collect(Collectors.toMap(Order::getUserId, Order::getStatus));

        for (IDVo idVo : idVos) {
            if (idVo.getParentId().equals(parentId)) {
                calcAchievementByUser(idVo.getSubIDVos(), achievementMap, idVo.getId());
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

    private List<User> getUsers() {
        return userMapper.selectAll();
    }

    private List<Order> getOrders(List<User> users, Predicate<Order> predicate) {
        //uid -> parentId
        //List<IDVo> idVos = new ArrayList<>();
        Set<Integer> ids = users.stream().map(User::getId).collect(Collectors.toSet());

        List<Order> orders = orderMapper.selectByUidSet(ids);
        return orders.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 更新业绩条目
     */
    private void updateAchievementInDB(List<Achievement> achievements) {
        log.info("achievements=\n{}", JSONUtils.printFormat(achievements));
        if (achievements != null && achievements.size() > 0) {
            int i = achievementMapper.insertBatch(achievements);
            log.info("update number is {}", i);
        }
    }

    private List<IDVo> createIDVos(List<User> users) {
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
