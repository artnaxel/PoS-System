package com.demo.PoS.repository;

import com.demo.PoS.model.entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, UUID> {
}
