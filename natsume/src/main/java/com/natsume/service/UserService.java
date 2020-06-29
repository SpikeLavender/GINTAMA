package com.natsume.service;

import com.natsume.entity.User;
import com.natsume.form.UserLoginForm;
import com.natsume.form.WeChartForm;
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

    /**
     * 登录
     */
    ResponseVo<User> wxLogin(WeChartForm weChartForm);

    ResponseVo blind(Integer uid, Integer parentId);
}
