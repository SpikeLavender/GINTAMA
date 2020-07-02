package com.natsumes.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeChartForm {

    @NotBlank
    private String userCode;

    @NotBlank
    private String username;

    private Integer parentId;

}
