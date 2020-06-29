package com.natsume.service.impl;


import com.natsume.ApplicationTests;
import com.natsume.entity.User;
import com.natsume.enums.ResponseEnum;
import com.natsume.enums.RoleEnum;
import com.natsume.service.UserService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class UserServiceImplTest extends ApplicationTests {

	public static final String USERNAME = "natsume";

	public static final String PASSWORD = "123456";

	@Autowired
	private UserService userService;

	@Before
	public void register() {
		User user = new User(USERNAME, PASSWORD, "hetengjiao_xjtu@163.com", RoleEnum.ADMIN.getCode());
		ResponseVo<User> responseVo = userService.register(user);
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}


	@Test
	public void login() {
		ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
    public void blind() {
        ResponseVo responseVo = userService.blind(12, 14);
        log.info("responseVo={}", JSONUtils.printFormat(responseVo));
        //Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}