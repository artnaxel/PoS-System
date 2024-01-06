package com.demo.PoS.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class ServiceSlotDetails {

    private UUID providedServiceId;

    private UUID employeeId;

    private LocalTime startTime;

    private LocalTime endTime;
}
