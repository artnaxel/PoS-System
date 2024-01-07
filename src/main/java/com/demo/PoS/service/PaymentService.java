package com.demo.PoS.service;

import com.demo.PoS.dto.PaymentDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(PaymentDto paymentDto) {
        //Leaving it commented for easier testing reasons - uncomment before pushing to prod
        //Order can not be created because missing customer id. Customer is not initialized
//        Order order = orderRepository.findById(paymentDto.orderId()).orElseThrow();
        Payment payment = Payment.builder()
//                .order(order)
                .amount(paymentDto.amount())
                .paymentMethod(paymentDto.paymentMethod())
                .paymentDateTime(new Date())
                .paymentStatus(paymentDto.paymentStatus())
                .build();
        return paymentRepository.save(payment);
    }

    public List<Payment> findAllPayemtns() {
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
    public Payment updatePayment(UUID paymentId, PaymentDto paymentDetails) {
        Payment updatedPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + paymentId));

        updatedPayment.setAmount(paymentDetails.amount());
        updatedPayment.setPaymentMethod(paymentDetails.paymentMethod());
        updatedPayment.setPaymentStatus(paymentDetails.paymentStatus());
        updatedPayment.setRefundAmount(paymentDetails.refundAmount());
        updatedPayment.setRefundDate(paymentDetails.refundDate());

        return paymentRepository.save(updatedPayment);
    }
}
