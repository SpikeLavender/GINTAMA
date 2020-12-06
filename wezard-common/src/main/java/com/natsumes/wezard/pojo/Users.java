package com.natsumes.wezard.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -3312996881761609616L;
    private Integer id;

    private String username;

    private String password;

    private String otherName;

    private String name;

    private String bank;

    private String bankNo;

    private String openid;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Integer parentId;

    private Date createTime;

    private Date updateTime;

}