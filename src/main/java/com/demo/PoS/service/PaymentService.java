package com.demo.PoS.service;

import com.demo.PoS.dto.payment.PaymentRequest;
import com.demo.PoS.dto.payment.PaymentResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.PaymentMapper;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new NotFoundException("Payment not found with: " + paymentRequest.getOrderId()));
        Optional<Payment> existingPayment = paymentRepository.findByOrderId(paymentRequest.getOrderId());

        if (existingPayment.isPresent()) {
            throw new IllegalStateException("Payment already exists for order ID: " + paymentRequest.getOrderId());
        }

        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentDateTime(LocalDateTime.now())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .build();
        return PaymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public List<PaymentResponse> findAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(PaymentMapper::toPaymentResponse).toList();
    }

    public void deletePayment(UUID paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new NotFoundException("Product not found with id: " + paymentId);
        }
        paymentRepository.deleteById(paymentId);
    }

    public PaymentResponse findPaymentById(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + paymentId));
        return PaymentMapper.toPaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse updatePayment(UUID paymentId, PaymentRequest paymentRequest) {
        Payment updatedPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + paymentId));

        updatedPayment.setAmount(paymentRequest.getAmount());
        updatedPayment.setPaymentMethod(paymentRequest.getPaymentMethod());
        updatedPayment.setPaymentStatus(paymentRequest.getPaymentStatus());
        updatedPayment.setRefundAmount(paymentRequest.getRefundAmount());
        updatedPayment.setRefundDate(paymentRequest.getRefundDate());

        paymentRepository.save(updatedPayment);

        return PaymentMapper.toPaymentResponse(updatedPayment);
    }
}
