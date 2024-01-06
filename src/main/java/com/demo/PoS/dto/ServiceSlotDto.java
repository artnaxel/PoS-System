package com.demo.PoS.dto;

import com.demo.PoS.model.enums.ServiceSlotStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class ServiceSlotDto {

    private UUID id;

    private UUID employeeId;

    private UUID providedServiceId;

    private LocalTime startTime;

    private LocalTime endTime;

    private ServiceSlotStatus serviceSlotStatus;
}
