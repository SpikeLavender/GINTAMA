package com.natsume.controller;

import com.natsume.entity.User;
import com.natsume.form.UserLoginForm;
import com.natsume.form.UserRegisterForm;
import com.natsume.service.UserService;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static com.natsume.consts.NatsumeConst.CURRENT_USER;
import static com.natsume.enums.ResponseEnum.PARAM_ERROR;

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
}
