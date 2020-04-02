package com.natsume.controller;

import com.github.pagehelper.PageInfo;
import com.natsume.entity.User;
import com.natsume.service.AchievementService;
import com.natsume.vo.AchievementVo;
import com.natsume.vo.ProfitVo;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.natsume.consts.NatsumeConst.CURRENT_USER;

@RestController
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/achievement")
    public ResponseVo<AchievementVo> list(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return achievementService.list(user.getId());
    }

    @GetMapping("/achievement/profit")
    public ResponseVo<ProfitVo> profit(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return achievementService.profit(user.getId());
    }

    @GetMapping("/achievement/detail")
    public ResponseVo<PageInfo> detail(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                       HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return achievementService.detail(user.getId(), pageNum, pageSize);
    }

//    @GetMapping("/achievement/paid")
//    public ResponseVo<PageInfo> paid(Integer id, HttpSession session) {
//        User user = (User) session.getAttribute(CURRENT_USER);
//        return achievementService.detail(user.getId(), pageNum, pageSize);
//    }
}
