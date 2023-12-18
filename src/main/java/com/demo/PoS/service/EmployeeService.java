package com.demo.PoS.service;

import com.demo.PoS.dto.EmployeeDetails;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.exceptions.UserNotFoundException;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.ProvidedServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with id: " + employeeId));
    }

    public Employee updateEmployee(UUID employeeId, EmployeeDetails employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with id: " + employeeId));

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setSurname(employeeDetails.getSurname());

        if (employeeDetails.getServiceIds() != null) {
            Set<ProvidedService> services = employeeDetails.getServiceIds().stream()
                    .map(serviceId -> providedServiceRepository.findById(serviceId)
                            .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId)))
                    .collect(Collectors.toSet());
            existingEmployee.setProvidedServices(services);
        }

        return employeeRepository.save(existingEmployee);
    }

    // Delete an employee
    public void deleteEmployee(UUID employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new UserNotFoundException("Employee not found with id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

}
