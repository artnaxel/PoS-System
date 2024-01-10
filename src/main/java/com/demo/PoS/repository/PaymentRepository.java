package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Set<Payment> findByOrderId(UUID orderId);
}

