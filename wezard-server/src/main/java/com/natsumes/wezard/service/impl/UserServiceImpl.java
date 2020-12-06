package com.natsumes.wezard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.natsumes.wezard.config.WxConfig;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.UserBankForm;
import com.natsumes.wezard.entity.form.WeChartForm;
import com.natsumes.wezard.enums.ResponseEnum;
import com.natsumes.wezard.enums.RoleEnum;
import com.natsumes.wezard.mapper.UsersMapper;
import com.natsumes.wezard.pojo.Users;
import com.natsumes.wezard.service.UserService;
import com.natsumes.wezard.util.JSONUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response<Users> register(Users users) {
        //username 不能重复
        int countByUsername = usersMapper.countByUsername(users.getUsername());
        if (countByUsername > 0) {
            return Response.error(ResponseEnum.USERNAME_EXIST);
        }

        // 检验推广父id是否有效
        if (users.getParentId() == null) {
            users.setParentId(0);
        }
        if (users.getParentId() != 0 && usersMapper.countById(users.getParentId()) <= 0) {
            //todo: 设置为0，是否需要提示异常待定
            return Response.error(ResponseEnum.PARENT_NO_EXIST, "上级ID错误，请确认并重新填写");
        }

        users.setRole(RoleEnum.ADMIN.getCode());
        //MD5摘要算法(Spring 自带)
        String password = new BCryptPasswordEncoder().encode(users.getPassword());
        users.setPassword(password);

        //写入数据库
        int resultCount = usersMapper.insertSelective(users);
        if (resultCount == 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR, "写入数据库异常, 注册失败");
        }
        return Response.success();
    }

    /**
     * cookie 跨域
     * todo: session保存在内存里, 改进版本: token+redis
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Response<Users> login(String username, String password) {
        Users users = usersMapper.selectByUsername(username);
        if (users == null) {
            //用户不存在(返回: 用户名或密码错误)
            return Response.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if (!users.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误(返回: 用户名或密码错误)
            return Response.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        users.setPassword("");

        return Response.success(users);
    }

    @Override
    public Response<Users> wxLogin(WeChartForm weChartForm) {


        String url = wxConfig.getOpenIdUrl() + "?appid=" + wxConfig.getAppId()
                + "&secret=" + wxConfig.getMchKey() + "&js_code="
                + weChartForm.getUserCode() + "&grant_type=authorization_code";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return Response.error(ResponseEnum.SYSTEM_ERROR, "查询微信OpenId失败");
        }

        JSONObject responseBody = JSONUtils.parseObject(responseEntity.getBody());
        if (responseBody.containsKey("errcode")) {
            return Response.error(ResponseEnum.WECHART_LOGIN_ERROR, responseBody.getString("errmsg"));
        }
        String openId = responseBody.getString("openid");
        String sessionKey = responseBody.getString("session_key");

        //openId为用户的username
        Users users = usersMapper.selectByUsername(openId);
        if (users == null) {
            //用户不存在(返回: 用户名或密码错误) , 自动注册
            users = new Users();
            users.setRole(RoleEnum.CUSTOMER.getCode());
            users.setOtherName(weChartForm.getUsername());
            //MD5摘要算法(Spring 自带)
            //存储sessionKey
            users.setPassword(openId);
            users.setParentId(0);
            users.setUsername(openId);
            users.setOpenid(sessionKey);

            //写入数据库
            int id = usersMapper.insertSelective(users);
            if (id == -1) {
                return Response.error(ResponseEnum.SYSTEM_ERROR, "写入数据库异常, 注册失败");
            }
            users.setId(id);
        } else {
            //更新sessionKey
            users.setPassword(openId);
            users.setId(users.getId());
            users.setUsername(openId);
            users.setOtherName(weChartForm.getUsername());
            usersMapper.updateByPrimaryKey(users);
        }
        return Response.success(users);
    }

    @Override
    public Response blindParent(Integer uid, Integer parentId) {
        if (parentId == 0) {
            return Response.success();
        }

        //判断此用户是否绑定不为0的父Id
        Users user = usersMapper.selectByPrimaryKey(uid);
        //已绑定，直接返回已绑定
        if (user.getParentId() != 0) {
            return Response.error(ResponseEnum.PARENT_HAS_EXIST);
        }

        //未绑定
        // 去查parentId是不是不为0且不存在数据库中
        if (usersMapper.countById(parentId) <= 0) {
            // 是，返回，此父Id错误
            return Response.error(ResponseEnum.PARENT_NO_EXIST);
        }
        // 去查parentId是不是不为此用户的子Id
        List<Users> users = usersMapper.selectAll();
        if (isSubId(users, uid, parentId)) {
            // 是，返回，此ID是你的下级，不能绑定
            return Response.error(ResponseEnum.PARENT_IS_NOT_VALID);
        }

        //否，绑定，0 -> 绑定0
        user.setParentId(parentId);
        int i = usersMapper.updateByPrimaryKeySelective(user);
        if (i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR);
        }
        //返回成功
        return Response.success();
    }

    @Override
    public Response blindBank(Integer userId, UserBankForm userBankForm) {
        Users users = new Users();
        BeanUtils.copyProperties(userBankForm, users);
        users.setId(userId);
        int i = usersMapper.updateByPrimaryKeySelective(users);
        if (i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR);
        }
        return Response.success("绑定银行卡成功");
    }

    private boolean isSubId(List<Users> users, Integer uId, Integer parentId) {
        for (Users user : users) {
            if (!user.getId().equals(parentId)) {
                continue;
            }
            if (user.getParentId().equals(uId)) {
                return true;
            } else {
                return isSubId(users, uId, user.getParentId());
            }
        }
        return false;
    }
}
