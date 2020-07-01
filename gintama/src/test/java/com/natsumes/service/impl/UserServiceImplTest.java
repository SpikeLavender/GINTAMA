package com.natsumes.service.impl;


import com.natsumes.ApplicationTests;
import com.natsumes.entity.User;
import com.natsumes.enums.ResponseEnum;
import com.natsumes.enums.RoleEnum;
import com.natsumes.form.WeChartForm;
import com.natsumes.service.UserService;
import com.natsumes.utils.JSONUtils;
import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class UserServiceImplTest extends ApplicationTests {

	public static final String USERNAME = "natsumes";

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

    @Test
    public void wxlogin() {
        WeChartForm weChartForm = new WeChartForm();
        weChartForm.setParentId(0);
        weChartForm.setUserCode("033KjAv50OHLkE1nTtu50z3Rv50KjAvh");
        ResponseVo<User> responseVo = userService.wxLogin(weChartForm);
        log.info("responseVo={}", JSONUtils.printFormat(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}