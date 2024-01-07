package com.demo.PoS.dto;

import com.demo.PoS.model.enums.PaymentMethod;
import com.demo.PoS.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record PaymentDto(
        UUID orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        BigDecimal refundAmount,
        Date refundDate
) {
}
