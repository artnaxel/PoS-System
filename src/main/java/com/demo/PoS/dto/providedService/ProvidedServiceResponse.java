package com.demo.PoS.dto.providedService;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProvidedServiceResponse {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID discountId;
}

