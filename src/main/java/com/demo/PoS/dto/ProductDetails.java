package com.demo.PoS.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductDetails {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;
}
