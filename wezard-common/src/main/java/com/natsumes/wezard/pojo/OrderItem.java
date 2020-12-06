package com.natsumes.wezard.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = -5925872013658604156L;
    private Integer id;

    private Integer userId;

    private String orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;
}