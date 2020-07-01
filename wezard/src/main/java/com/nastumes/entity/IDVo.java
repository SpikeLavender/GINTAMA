package com.nastumes.entity;

import com.natsumes.entity.Achievement;
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
