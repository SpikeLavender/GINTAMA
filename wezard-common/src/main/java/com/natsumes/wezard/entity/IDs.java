package com.natsumes.wezard.entity;

import com.natsumes.wezard.pojo.Achievement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IDs implements Serializable {
    private static final long serialVersionUID = -8412635707783201268L;
    private Integer id;

    private Integer parentId;

    private List<IDs> subIdVos;

    private Boolean level;

    private Achievement achievement;
}
