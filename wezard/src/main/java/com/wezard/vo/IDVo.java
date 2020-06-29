package com.wezard.vo;

import com.wezard.entity.Achievement;
import lombok.Data;

import java.util.List;

@Data
public class IDVo {
    private Integer id;

    private Integer parentId;

    private List<IDVo> subIDVos;

    private Boolean level;

    private Achievement achievement;
}
