package com.demo.PoS.dto.serviceSlot;

import com.demo.PoS.model.enums.ServiceSlotStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ServiceSlotResponse {

    private UUID id;
    private UUID employeeId;
    private UUID providedServiceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ServiceSlotStatus serviceSlotStatus;
}
