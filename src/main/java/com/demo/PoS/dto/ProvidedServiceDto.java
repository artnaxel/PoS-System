package com.demo.PoS.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProvidedServiceDto {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID discountId;
}

