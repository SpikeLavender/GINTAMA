package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class WeChartForm implements Serializable {

    private static final long serialVersionUID = 3655738432346262095L;
    @NotBlank
    private String userCode;

    @NotBlank
    private String username;

    private Integer parentId;

}
