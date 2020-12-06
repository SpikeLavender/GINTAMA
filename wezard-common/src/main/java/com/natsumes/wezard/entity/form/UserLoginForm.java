package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class UserLoginForm implements Serializable {

    private static final long serialVersionUID = -7881782690437660121L;

    /**
     * NotBlank 用于 String 判断空格
     * NotEmpty 用于集合
     * NotNull
     */
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
