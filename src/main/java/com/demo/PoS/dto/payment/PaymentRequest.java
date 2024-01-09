package com.demo.PoS.dto.payment;

import com.demo.PoS.model.enums.PaymentMethod;
import com.demo.PoS.model.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentRequest {

    private UUID orderId;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private PaymentStatus paymentStatus;

    private BigDecimal refundAmount;

    private LocalDateTime refundDate;
}
