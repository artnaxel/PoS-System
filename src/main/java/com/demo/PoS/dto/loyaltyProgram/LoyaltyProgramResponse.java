package com.demo.PoS.dto.loyaltyProgram;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoyaltyProgramResponse {

    private UUID id;
    private String name;
    private BigDecimal discountRate;
}