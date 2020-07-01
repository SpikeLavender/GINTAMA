package com.natsumes.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchForm {

    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer categoryId;

    public SearchForm() {
    }

    public SearchForm(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer categoryId) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoryId = categoryId;
    }
}
