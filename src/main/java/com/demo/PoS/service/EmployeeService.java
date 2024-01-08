package com.demo.PoS.service;

import com.demo.PoS.dto.employee.EmployeeRequest;
import com.demo.PoS.dto.employee.EmployeeResponse;
import com.demo.PoS.mappers.EmployeeMapper;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeResponse> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(EmployeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .build();

        employeeRepository.save(employee);

        return EmployeeMapper.toEmployeeResponse(employee);
    }


    public EmployeeResponse findEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));

        return EmployeeMapper.toEmployeeResponse(employee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(UUID employeeId, EmployeeRequest employeeRequest) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));

        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setSurname(employeeRequest.getSurname());

        employeeRepository.save(existingEmployee);

        return EmployeeMapper.toEmployeeResponse(existingEmployee);
    }

    public void deleteEmployee(UUID employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new NotFoundException("Employee not found with id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }
}
