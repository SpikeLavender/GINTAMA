package com.natsume.schedule;

import com.natsume.entity.Achievement;
import com.natsume.entity.Order;
import com.natsume.entity.User;
import com.natsume.enums.AchievementTypeEnum;
import com.natsume.enums.LevelEnum;
import com.natsume.enums.OrderStatusEnum;
import com.natsume.mapper.AchievementMapper;
import com.natsume.mapper.OrderMapper;
import com.natsume.mapper.UserMapper;
import com.natsume.utils.DateUtils;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.IDVo;
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
public class CommissionScheduled {


}
