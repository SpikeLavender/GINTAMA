package com.natsumes.wezard.controller;

import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.UserBankForm;
import com.natsumes.wezard.entity.form.UserLoginForm;
import com.natsumes.wezard.entity.form.UserRegisterForm;
import com.natsumes.wezard.entity.form.WeChartForm;
import com.natsumes.wezard.pojo.Users;
import com.natsumes.wezard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsumes.wezard.config.StefanieConst.CURRENT_USER;

@RestController
@Slf4j
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Response register(@Valid @RequestBody UserRegisterForm userRegisterForm) {

        Users users = new Users();
        BeanUtils.copyProperties(userRegisterForm, users);

        return userService.register(users);
    }

    @PostMapping("/login")
    public Response<Users> login(@Valid @RequestBody UserLoginForm userLoginForm, HttpSession session) {

        Response<Users> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置 Session
        session.setAttribute(CURRENT_USER, userResponseVo.getData());
        log.info("login sessionId = {}", session.getId());
        return userResponseVo;
    }

    @PostMapping("/wechart")
    public Response<Users> wechart(@Valid @RequestBody WeChartForm userForm) {

        return userService.wxLogin(userForm);
    }

    @PostMapping("/{userId}/logout")
    public Response<Users> logout(@PathVariable Integer userId) {
        log.info("logout sessionId = {}", userId);

//        session.removeAttribute(CURRENT_USER);
        return Response.success();
    }

    @PostMapping("/{userId}/{parentId}")
    public Response blindParent(@PathVariable Integer userId, @PathVariable Integer parentId) {
        return userService.blindParent(userId, parentId);
    }

    @PutMapping("/{userId}/bank")
    public Response blindBank(@PathVariable Integer userId, @Valid @RequestBody UserBankForm userBankForm) {
        return userService.blindBank(userId, userBankForm);
    }
}
