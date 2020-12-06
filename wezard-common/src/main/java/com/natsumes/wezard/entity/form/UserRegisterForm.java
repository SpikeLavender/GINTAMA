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
public class UserRegisterForm implements Serializable {

    private static final long serialVersionUID = -4886824318831615133L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Integer parentId;
}
