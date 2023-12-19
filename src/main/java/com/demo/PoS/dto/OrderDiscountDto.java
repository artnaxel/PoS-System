package com.demo.PoS.dto;

import com.demo.PoS.model.enums.DiscountType;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDiscountDto(
        UUID orderId,
        DiscountType discountType,
        BigDecimal discountAmount
) {
}
