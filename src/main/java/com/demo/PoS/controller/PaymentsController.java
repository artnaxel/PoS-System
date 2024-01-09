package com.demo.PoS.controller;

import com.demo.PoS.dto.payment.PaymentRequest;
import com.demo.PoS.dto.payment.PaymentResponse;
import com.demo.PoS.model.entity.Payment;
import com.demo.PoS.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping
    ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse savedPayment = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.findAllPayments();
        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable UUID paymentId) {
        PaymentResponse payment = paymentService.findPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePaymentData(@PathVariable UUID paymentId, @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse updatedPayment = paymentService.updatePayment(paymentId, paymentRequest);
        return ResponseEntity.ok(updatedPayment);
    }

}
