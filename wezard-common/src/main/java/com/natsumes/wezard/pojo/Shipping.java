package com.natsumes.wezard.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Shipping implements Serializable {

    private static final long serialVersionUID = 6025443114383753122L;

    private Integer id;

    private Integer userId;

    private Boolean isDefault;

    private Integer status;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Date createTime;

    private Date updateTime;

}