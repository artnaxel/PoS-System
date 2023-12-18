package com.demo.PoS.service;

import com.demo.PoS.dto.ProvidedServiceDetails;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ProvidedServiceService {

    private final ProvidedServiceRepository providedServiceRepository;

    private final EmployeeRepository employeeRepository;

    public ProvidedServiceService(ProvidedServiceRepository providedServiceRepository, EmployeeRepository employeeRepository) {
        this.providedServiceRepository = providedServiceRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<ProvidedService> findAll() {
        return providedServiceRepository.findAll();
    }

    @Transactional
    public ProvidedService createProvidedService(ProvidedServiceDetails providedServiceDetails) {
        Set<Employee> employees = Optional.ofNullable(providedServiceDetails.getEmployeeIds())
                .orElse(Collections.emptySet())
                .stream()
                .map(id -> employeeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id)))
                .collect(Collectors.toSet());

        ProvidedService providedService = ProvidedService.builder()
                .name(providedServiceDetails.getName())
                .description(providedServiceDetails.getDescription())
                .price(providedServiceDetails.getPrice())
                .employees(employees)
                .build();

        return providedServiceRepository.save(providedService);
    }

    public ProvidedService findById(UUID providedServiceId) {
        return providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));
    }

    @Transactional
    public ProvidedService updateProvidedService(UUID providedServiceId, ProvidedServiceDetails details) {
        ProvidedService providedService = providedServiceRepository.findById(providedServiceId)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + providedServiceId));

        providedService.setName(details.getName());
        providedService.setDescription(details.getDescription());
        providedService.setPrice(details.getPrice());

        if (details.getEmployeeIds() != null) {
            Set<Employee> employees = details.getEmployeeIds().stream()
                    .map(id -> employeeRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id)))
                    .collect(Collectors.toSet());
            providedService.setEmployees(employees);
        }

        return providedServiceRepository.save(providedService);
    }

    public void deleteById(UUID providedServiceId) {
        if(!providedServiceRepository.existsById(providedServiceId)) {
            throw new NotFoundException("Employee not found with id: " + providedServiceId);
        }
        providedServiceRepository.deleteById(providedServiceId);
    }

}
