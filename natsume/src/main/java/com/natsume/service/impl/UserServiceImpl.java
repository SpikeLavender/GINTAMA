package com.natsume.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.natsume.config.WxConfig;
import com.natsume.entity.User;
import com.natsume.enums.RoleEnum;
import com.natsume.mapper.UserMapper;
import com.natsume.service.UserService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static com.natsume.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private WxConfig wxConfig;

	@Autowired
    private RestTemplate restTemplate;

	@Override
	public ResponseVo<User> register(User user) {
		//username 不能重复
		int countByUsername = userMapper.countByUsername(user.getUsername());
		if (countByUsername > 0) {
			return ResponseVo.error(USERNAME_EXIST);
		}

		//email 不能重复
//		int countByEmail = userMapper.countByEmail(user.getEmail());
//		if (countByEmail > 0) {
//			return ResponseVo.error(EMAIL_EXIST);
//		}

		//检验推广父id是否有效

        if (user.getParentId() != 0 && userMapper.countById(user.getParentId()) <= 0) {
            //todo: 设置为0，是否需要提示异常待定
            return ResponseVo.error(PARENT_NO_EXIST, "上级ID错误，请确认并重新填写");
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

    @Override
    public ResponseVo<User> wxLogin(String userCode) {


        String url = wxConfig.getOpenIdUrl() + "?appid=" + wxConfig.getAppId()
                + "&secret=" + wxConfig.getMchKey()+ "&js_code="
                + userCode + "&grant_type=authorization_code";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return ResponseVo.error(SYSTEM_ERROR, "查询微信OpenId失败");
        }

        JSONObject responseBody = JSONUtils.parseObject(responseEntity.getBody());
        if (responseBody.containsKey("errcode")) {
            return ResponseVo.error(WECHART_LOGIN_ERROR, responseBody.getString("errmsg"));
        }
        String openId = responseBody.getString("openid");
        String sessionKey = responseBody.getString("session_key");

        User user = userMapper.selectByUsername(openId); //openId为用户的username
        if (user == null) {
            //用户不存在(返回: 用户名或密码错误) , 自动注册
            user = new User();
            user.setRole(RoleEnum.CUSTOMER.getCode());
            //MD5摘要算法(Spring 自带)
            //存储sessionKey
            user.setPassword(sessionKey);
            user.setParentId(0);
            user.setUsername(openId);

            //写入数据库
            int resultCount = userMapper.insertSelective(user);
            if (resultCount == 0) {
                return ResponseVo.error(SYSTEM_ERROR, "写入数据库异常, 注册失败");
            }
        } else {
            //更新sessionKey
            user.setPassword(sessionKey);
            userMapper.updateByPrimaryKey(user);
        }
        return ResponseVo.success(user);
    }
}
