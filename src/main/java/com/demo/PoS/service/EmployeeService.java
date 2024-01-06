package com.demo.PoS.service;

import com.demo.PoS.dto.EmployeeDetails;
import com.demo.PoS.dto.EmployeeDto;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.exceptions.NotFoundException;
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

    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee ->
                EmployeeDto.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .surname(employee.getSurname())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeDetails employeeDetails) {
        Employee employee = Employee.builder()
                .name(employeeDetails.getName())
                .surname(employeeDetails.getSurname())
                .build();

        employeeRepository.save(employee);

        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build();
    }


    public EmployeeDto findEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));

        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build();
    }

    @Transactional
    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDetails employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setSurname(employeeDetails.getSurname());

        employeeRepository.save(existingEmployee);

        return EmployeeDto.builder()
                .id(existingEmployee.getId())
                .name(existingEmployee.getName())
                .surname(existingEmployee.getSurname())
                .build();
    }

    public void deleteEmployee(UUID employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new NotFoundException("Employee not found with id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }
}
