package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class ShippingForm implements Serializable {

    private static final long serialVersionUID = -4374495155167347381L;
    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;

    @NotNull
    private Boolean isDefault;

}
