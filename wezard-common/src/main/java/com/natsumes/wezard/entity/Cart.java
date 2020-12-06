package com.natsumes.wezard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart implements Serializable {

    private static final long serialVersionUID = 109421235264497974L;
    private Integer productId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    /**
     * 商品是否选中
     */
    private Boolean productSelected;
}
