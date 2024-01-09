package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Customer;
import com.demo.PoS.model.entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByLoyaltyProgram(LoyaltyProgram loyaltyProgram);
}
