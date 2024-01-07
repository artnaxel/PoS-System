package com.demo.PoS.dto.order;

import com.demo.PoS.model.enums.DiscountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderDiscountDto(
        UUID orderId,
        DiscountType discountType,
        BigDecimal discountAmount
) {
}
