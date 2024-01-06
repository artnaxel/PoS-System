package com.demo.PoS.dto.serviceSlot;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ServiceSlotRequest {

    @NotNull
    private UUID providedServiceId;

    @NotNull
    private UUID employeeId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
