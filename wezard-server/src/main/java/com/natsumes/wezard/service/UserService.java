package com.natsumes.wezard.service;


import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.UserBankForm;
import com.natsumes.wezard.entity.form.WeChartForm;
import com.natsumes.wezard.pojo.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 注册
     *
     * @param users
     * @return
     */
    Response<Users> register(Users users);

    /**
     * 登录
     */
    Response<Users> login(String username, String password);

    /**
     * 登录
     */
    Response<Users> wxLogin(WeChartForm weChartForm);

    Response blindParent(Integer uid, Integer parentId);

    Response blindBank(Integer userId, UserBankForm userBankForm);

}
