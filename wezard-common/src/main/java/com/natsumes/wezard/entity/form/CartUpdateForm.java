package com.natsumes.wezard.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class CartUpdateForm implements Serializable {

    private static final long serialVersionUID = 3944532563893209452L;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;

}
