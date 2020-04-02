package com.natsume.vo;

import com.natsume.entity.Achievement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class IDVo {
    private Integer id;

    private Integer parentId;

    private List<IDVo> subIDVos;

    private Boolean level;

    private Achievement achievement;
}
