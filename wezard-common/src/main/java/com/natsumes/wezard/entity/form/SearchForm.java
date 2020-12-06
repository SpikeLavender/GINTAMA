package com.natsumes.wezard.entity.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm implements Serializable {

    private static final long serialVersionUID = 1222737238449394740L;
    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer categoryId;
}
