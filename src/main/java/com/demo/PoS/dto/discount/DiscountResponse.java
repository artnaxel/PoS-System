package com.demo.PoS.dto.discount;

import com.demo.PoS.model.enums.DiscountStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DiscountResponse {

    private UUID id;
    private String name;
    private BigDecimal discountRate;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private DiscountStatus discountStatus;
}
