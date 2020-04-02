package com.natsume.service.impl;

import com.natsume.entity.User;
import com.natsume.enums.RoleEnum;
import com.natsume.mapper.UserMapper;
import com.natsume.service.UserService;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.natsume.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseVo<User> register(User user) {
		//username 不能重复
		int countByUsername = userMapper.countByUsername(user.getUsername());
		if (countByUsername > 0) {
			return ResponseVo.error(USERNAME_EXIST);
		}

		//email 不能重复
		int countByEmail = userMapper.countByEmail(user.getEmail());
		if (countByEmail > 0) {
			return ResponseVo.error(EMAIL_EXIST);
		}

		//检验推广父id是否有效
        if (user.getParentId() != null && userMapper.countById(user.getParentId()) <= 0) {
            //todo: 设置为0，是否需要提示异常待定
            user.setParentId(0);
        }

        user.setRole(RoleEnum.ADMIN.getCode());
		//MD5摘要算法(Spring 自带)
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

		//写入数据库
		int resultCount = userMapper.insertSelective(user);
		if (resultCount == 0) {
			return ResponseVo.error(SYSTEM_ERROR, "写入数据库异常, 注册失败");
		}
		return ResponseVo.success();
	}

	//cookie 跨域
	//todo: session保存在内存里, 改进版本: token+redis
	@Override
	public ResponseVo<User> login(String username, String password) {
		User user = userMapper.selectByUsername(username);
		if (user == null) {
			//用户不存在(返回: 用户名或密码错误)
			return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
		}
		if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
			//密码错误(返回: 用户名或密码错误)
			return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
		}

		user.setPassword("");

		return ResponseVo.success(user);
	}

}
