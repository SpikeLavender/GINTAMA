package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class CartAddForm implements Serializable {

    private static final long serialVersionUID = -8000004829654197170L;
    @NotNull
    private Integer productId;

    private Boolean selected = true;

    private Integer quantity = 1;
}
