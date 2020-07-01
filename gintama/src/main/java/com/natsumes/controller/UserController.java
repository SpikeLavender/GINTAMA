package com.natsumes.controller;

import com.natsumes.entity.User;
import com.natsumes.form.UserLoginForm;
import com.natsumes.form.UserRegisterForm;
import com.natsumes.form.WeChartForm;
import com.natsumes.service.UserService;
import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsumes.consts.NatsumeConst.CURRENT_USER;

@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/register")
	public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm) {

		User user = new User();
		BeanUtils.copyProperties(userRegisterForm, user);

		return userService.register(user);
	}

	@PostMapping("/user/login")
	public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm, HttpSession session) {

		ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

		//设置 Session
		session.setAttribute(CURRENT_USER, userResponseVo.getData());
		log.info("login sessionId = {}", session.getId());
		return userResponseVo;
	}

    @PostMapping("/user/wechart")
    public ResponseVo<User> wechart(@Valid @RequestBody WeChartForm userForm, HttpSession session) {

        ResponseVo<User> userResponseVo = userService.wxLogin(userForm);

        //设置 Session
        session.setAttribute(CURRENT_USER, userResponseVo.getData());
        log.info("login sessionId = {}", session.getId());
        return userResponseVo;
    }

	@GetMapping("/user")
	public ResponseVo<User> login(HttpSession session) {
		log.info("user sessionId = {}", session.getId());
		User user = (User) session.getAttribute(CURRENT_USER);
		return ResponseVo.success(user);
	}

	@PostMapping("/user/logout")
	public ResponseVo<User> logout(HttpSession session) {
		log.info("logout sessionId = {}", session.getId());

		session.removeAttribute(CURRENT_USER);
		return ResponseVo.success();
	}

	@PostMapping("/user/{parentId}")
    public ResponseVo blind(@PathVariable Integer parentId,
                            HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return userService.blind(user.getId(), parentId);
    }
}
