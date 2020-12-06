package com.natsumes.wezard.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = -4963728529839957383L;
    private Integer id;

    private Integer userId;

    private Integer productId;

    private String username;

    private String content;

    private Integer giveLike;

    private Integer status;

    private Integer replyId;

    private Integer role;

    private String avatarUrl;

    private Date createTime;

    private Date updateTime;

}