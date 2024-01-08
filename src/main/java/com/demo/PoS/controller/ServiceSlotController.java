package com.demo.PoS.controller;

import com.demo.PoS.dto.serviceSlot.ServiceSlotRequest;
import com.demo.PoS.dto.serviceSlot.ServiceSlotResponse;
import com.demo.PoS.service.ServiceSlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/serviceSlots")
public class ServiceSlotController {

    private final ServiceSlotService serviceSlotService;

    public ServiceSlotController(ServiceSlotService serviceSlotService) {
        this.serviceSlotService = serviceSlotService;
    }

    @PostMapping
    public ResponseEntity<ServiceSlotResponse> createServiceSlot(@RequestBody @Valid ServiceSlotRequest request) {
        ServiceSlotResponse serviceSlot = serviceSlotService.createServiceSlot(request);
        return new ResponseEntity<>(serviceSlot, HttpStatus.CREATED);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceSlotResponse>> getAvailableSlotsByService(@PathVariable UUID serviceId) {
        List<ServiceSlotResponse> slots = serviceSlotService.getAvailableSlotsByService(serviceId);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ServiceSlotResponse>> getAvailableSlotsByEmployee(@PathVariable UUID employeeId) {
        List<ServiceSlotResponse> slots = serviceSlotService.getAvailableSlotsByEmployee(employeeId);
        return ResponseEntity.ok(slots);
    }
}

