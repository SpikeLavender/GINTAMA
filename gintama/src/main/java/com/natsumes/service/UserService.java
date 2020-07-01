package com.natsumes.service;

import com.natsumes.entity.User;
import com.natsumes.form.WeChartForm;
import com.natsumes.vo.ResponseVo;

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

    /**
     * 登录
     */
    ResponseVo<User> wxLogin(WeChartForm weChartForm);

    ResponseVo blind(Integer uid, Integer parentId);
}
