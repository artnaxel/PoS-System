package com.demo.PoS.service;

import com.demo.PoS.dto.serviceSlot.ServiceSlotRequest;
import com.demo.PoS.dto.serviceSlot.ServiceSlotResponse;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.model.entity.ServiceSlot;
import com.demo.PoS.model.enums.ServiceSlotStatus;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import com.demo.PoS.repository.ServiceSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceSlotService {

    private final ServiceSlotRepository serviceSlotRepository;
    private final EmployeeRepository employeeRepository;
    private final ProvidedServiceRepository providedServiceRepository;

    public ServiceSlotResponse createServiceSlot(ServiceSlotRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + request.getEmployeeId()));
        ProvidedService providedService = providedServiceRepository.findById(request.getProvidedServiceId())
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + request.getProvidedServiceId()));

        ServiceSlot serviceSlot = ServiceSlot.builder()
                .employee(employee)
                .providedService(providedService)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .serviceSlotStatus(ServiceSlotStatus.FREE)
                .build();

        serviceSlotRepository.save(serviceSlot);

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

