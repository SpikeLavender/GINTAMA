package com.natsumes.wezard.entity.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
@ToString
public class WxMessageForm implements Serializable {

    private static final long serialVersionUID = -7694591706161196814L;

    @JsonProperty("CreateTime")
    private String createTime;

    @JsonProperty("Event")
    private String event;

    @JsonProperty("ToUserName")
    private String toUserName;

    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("MsgType")
    private String msgType;

    @JsonProperty("SessionFrom")
    private String sessionFrom;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("Encrypt")
    private String encrypt;
}
