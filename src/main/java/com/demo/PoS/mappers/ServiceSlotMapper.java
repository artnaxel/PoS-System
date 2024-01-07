package com.demo.PoS.mappers;

import com.demo.PoS.dto.serviceSlot.ServiceSlotResponse;
import com.demo.PoS.model.entity.ServiceSlot;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceSlotMapper {
    public static ServiceSlotResponse toServiceSlotResponse(ServiceSlot serviceSlot) {
        return ServiceSlotResponse.builder()
                .id(serviceSlot.getId())
                .employeeId(serviceSlot.getEmployee().getId())
                .providedServiceId(serviceSlot.getProvidedService().getId())
                .startTime(serviceSlot.getStartTime())
                .endTime(serviceSlot.getEndTime())
                .serviceSlotStatus(serviceSlot.getServiceSlotStatus())
                .build();
    }
}
