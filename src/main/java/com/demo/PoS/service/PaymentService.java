package com.demo.PoS.service;

import com.demo.PoS.dto.payment.PaymentRequest;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public Payment createPayment(PaymentRequest paymentRequest) {

        Order order = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow();
        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentDateTime(LocalDateTime.now())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .build();
        return paymentRepository.save(payment);
    }

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public void deletePayment(UUID paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new NotFoundException("Product not found with id: " + paymentId);
        }
        paymentRepository.deleteById(paymentId);
    }

    public Payment findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + paymentId));
    }

    @Transactional
    public Payment updatePayment(UUID paymentId, PaymentRequest paymentDetails) {
        Payment updatedPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + paymentId));

        updatedPayment.setAmount(paymentDetails.getAmount());
        updatedPayment.setPaymentMethod(paymentDetails.getPaymentMethod());
        updatedPayment.setPaymentStatus(paymentDetails.getPaymentStatus());
        updatedPayment.setRefundAmount(paymentDetails.getRefundAmount());
        updatedPayment.setRefundDate(paymentDetails.getRefundDate());

        return paymentRepository.save(updatedPayment);
    }
}
