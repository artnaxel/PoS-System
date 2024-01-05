package com.demo.PoS.repository;

import com.demo.PoS.model.entity.ProvidedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, UUID> {
}
