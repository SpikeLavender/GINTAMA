package com.natsumes.entity;

import lombok.Data;

@Data
public class Cart {

    private Integer productId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    /**
     * 商品是否选中
     */
    private Boolean productSelected;


    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
