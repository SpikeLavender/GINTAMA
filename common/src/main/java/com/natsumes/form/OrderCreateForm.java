package com.natsumes.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;

    private Integer productNum;

}
