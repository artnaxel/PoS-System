package com.demo.PoS.dto.payment;

import com.demo.PoS.model.enums.PaymentMethod;
import com.demo.PoS.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentResponse {

    private UUID orderId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private BigDecimal refundAmount;

    private LocalDateTime refundDate;
}
