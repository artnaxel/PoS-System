package com.demo.PoS.controller;

import com.demo.PoS.dto.providedService.ProvidedServiceRequest;
import com.demo.PoS.dto.providedService.ProvidedServiceResponse;
import com.demo.PoS.service.ProvidedServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/providedServices")
public class ProvidedServiceController {

    private final ProvidedServiceService providedServiceService;

    public ProvidedServiceController(ProvidedServiceService providedServiceService) {
        this.providedServiceService = providedServiceService;
    }

    @GetMapping
    public ResponseEntity<List<ProvidedServiceResponse>> getAllProvidedServices() {
        List<ProvidedServiceResponse> services = providedServiceService.getAllProvidedServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProvidedServiceResponse> createProvidedService(@Valid @RequestBody ProvidedServiceRequest providedServiceRequest) {
        ProvidedServiceResponse createdService = providedServiceService.createProvidedService(providedServiceRequest);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ProvidedServiceResponse> getProvidedServiceById(@PathVariable UUID serviceId) {
        ProvidedServiceResponse service = providedServiceService.findById(serviceId);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ProvidedServiceResponse> updateProvidedService(@PathVariable UUID serviceId, @RequestBody ProvidedServiceRequest providedServiceRequest) {
        ProvidedServiceResponse updatedService = providedServiceService.updateProvidedService(serviceId, providedServiceRequest);
        return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteProvidedService(@PathVariable UUID serviceId) {
        providedServiceService.deleteById(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
