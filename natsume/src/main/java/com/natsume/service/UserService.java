package com.natsume.service;

import com.natsume.entity.User;
import com.natsume.vo.ResponseVo;

public interface UserService {

	/**
	 * 注册
	 *
	 * @param user
	 * @return
	 */
	ResponseVo<User> register(User user);

	/**
	 * 登录
	 */
	ResponseVo<User> login(String username, String password);
}
