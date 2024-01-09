package com.demo.PoS.mappers;

import com.demo.PoS.dto.payment.PaymentResponse;
import com.demo.PoS.model.entity.Payment;

public class PaymentMapper {
    public static PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .refundAmount(payment.getRefundAmount())
                .refundDate(payment.getRefundDate())
                .build();
    }
}
