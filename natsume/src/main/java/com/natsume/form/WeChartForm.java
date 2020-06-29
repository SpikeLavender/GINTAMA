package com.natsume.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeChartForm {

    @NotBlank
    private String userCode;

    private Integer parentId;

}
