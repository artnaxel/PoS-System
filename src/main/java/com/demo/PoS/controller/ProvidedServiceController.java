package com.demo.PoS.controller;

import com.demo.PoS.dto.ProvidedServiceDetails;
import com.demo.PoS.model.entity.ProvidedService;
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
    public ResponseEntity<List<ProvidedService>> getAllProvidedServices() {
        List<ProvidedService> services = providedServiceService.findAll();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProvidedService> createProvidedService(@Valid @RequestBody ProvidedServiceDetails providedServiceDetails) {
        ProvidedService newService = providedServiceService.createProvidedService(providedServiceDetails);
        return new ResponseEntity<>(newService, HttpStatus.CREATED);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ProvidedService> getProvidedServiceById(@PathVariable UUID serviceId) {
        ProvidedService service = providedServiceService.findById(serviceId);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ProvidedService> updateProvidedService(@PathVariable UUID serviceId, @RequestBody ProvidedServiceDetails providedServiceDetails) {
        ProvidedService updatedService = providedServiceService.updateProvidedService(serviceId, providedServiceDetails);
        return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteProvidedService(@PathVariable UUID serviceId) {
        providedServiceService.deleteById(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
