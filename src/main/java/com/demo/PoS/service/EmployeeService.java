package com.demo.PoS.service;

import com.demo.PoS.dto.EmployeeDetails;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ProvidedServiceRepository providedServiceRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ProvidedServiceRepository providedServiceRepository) {
        this.employeeRepository = employeeRepository;
        this.providedServiceRepository = providedServiceRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(EmployeeDetails employeeDetails) {
        Set<ProvidedService> services = Optional.ofNullable(employeeDetails.getServiceIds())
                .orElse(Collections.emptySet())
                .stream()
                .map(id -> providedServiceRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Service not found with id: " + id)))
                .collect(Collectors.toSet());

        Employee employee = Employee.builder()
                .name(employeeDetails.getName())
                .surname(employeeDetails.getSurname())
                .providedServices(services)
                .build();

        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));
    }

    @Transactional
    public Employee updateEmployee(UUID employeeId, EmployeeDetails employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setSurname(employeeDetails.getSurname());

        if (employeeDetails.getServiceIds() != null) {
            Set<ProvidedService> services = employeeDetails.getServiceIds().stream()
                    .map(serviceId -> providedServiceRepository.findById(serviceId)
                            .orElseThrow(() -> new NotFoundException("Service not found with id: " + serviceId)))
                    .collect(Collectors.toSet());
            existingEmployee.setProvidedServices(services);
        }

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new NotFoundException("Employee not found with id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

}
