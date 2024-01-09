package com.demo.PoS.repository;

import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.ProvidedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, UUID> {
    List<ProvidedService> findByLoyaltyProgram(LoyaltyProgram loyaltyProgram);
}
