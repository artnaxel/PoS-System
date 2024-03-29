package com.demo.PoS.model.entity;

import com.demo.PoS.model.enums.ServiceSlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSlot {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "providedService_id")
    private ProvidedService providedService;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ServiceSlotStatus serviceSlotStatus;
}

