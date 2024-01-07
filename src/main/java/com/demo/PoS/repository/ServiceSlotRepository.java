package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.model.entity.ServiceSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ServiceSlotRepository extends JpaRepository<ServiceSlot, UUID> {
    List<ServiceSlot> findAllByProvidedServiceAndStartTimeAfter(ProvidedService providedService, LocalDateTime startTime);
    List<ServiceSlot> findAllByEmployeeAndStartTimeAfter(Employee employee, LocalDateTime startTime);

}
