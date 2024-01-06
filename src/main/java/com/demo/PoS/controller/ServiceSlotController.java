package com.demo.PoS.controller;

import com.demo.PoS.dto.ServiceSlotDetails;
import com.demo.PoS.dto.ServiceSlotDto;
import com.demo.PoS.service.ServiceSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceSlots")
public class ServiceSlotController {

    private final ServiceSlotService serviceSlotService;

    public ServiceSlotController(ServiceSlotService serviceSlotService) {
        this.serviceSlotService = serviceSlotService;
    }

    @PostMapping
    public ResponseEntity<ServiceSlotDto> createServiceSlot(@RequestBody ServiceSlotDetails request) {
        ServiceSlotDto serviceSlot = serviceSlotService.createServiceSlot(request);
        return new ResponseEntity<>(serviceSlot, HttpStatus.CREATED);
    }
}

