package com.natsumes.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * {CreateTime=1548042266, Event=user_enter_tempsession, ToUserName=gh_e6198220cbff,
 *  FromUserName=oZvme4q2Oi7Dz3FChXc43kqw28, MsgType=event, SessionFrom=wxapp}
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
