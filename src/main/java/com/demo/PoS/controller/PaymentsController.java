package com.demo.PoS.controller;

import com.demo.PoS.dto.PaymentDto;
import com.demo.PoS.model.entity.Payment;
import com.demo.PoS.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    ResponseEntity<Payment> createPayment(@RequestBody PaymentDto paymentDto) {
        System.out.println("The value of number is: ");
        Payment savedPayment = paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayemtns();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID paymentId) {
        Payment payment = paymentService.findPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePaymentData(@PathVariable UUID paymentId, @RequestBody PaymentDto paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(paymentId, paymentDetails);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

}
