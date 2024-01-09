package com.demo.PoS.repository;

import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByLoyaltyProgram(LoyaltyProgram loyaltyProgram);

    List<Product> findByDiscount_Id(UUID discountId);
}
