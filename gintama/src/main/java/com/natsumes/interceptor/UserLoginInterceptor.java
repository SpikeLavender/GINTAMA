package com.natsumes.interceptor;

import com.natsumes.entity.User;
import com.natsumes.exception.UserLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.natsumes.consts.NatsumeConst.CURRENT_USER;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
	/**
	 * true 表示流程继续, false 表示中断
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle...");
		User user = (User) request.getSession().getAttribute(CURRENT_USER);
		if (user == null) {
			log.info("user = null");
			throw new UserLoginException();
		}
		return true;
	}
}
