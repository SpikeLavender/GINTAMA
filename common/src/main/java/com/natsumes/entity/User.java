package com.natsumes.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String openid;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Integer parentId;

    private Date createTime;

    private Date updateTime;

    public User() {

    }

    public User(String username, String password, String email, Integer role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}