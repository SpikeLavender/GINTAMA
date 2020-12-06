package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class AchievementForm implements Serializable {
    private static final long serialVersionUID = -4459682999218161832L;

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    private List<Integer> ids;

    private Date completeTime;

    private Integer status;
}
