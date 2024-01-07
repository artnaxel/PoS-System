package com.demo.PoS.controller;

import com.demo.PoS.dto.employee.EmployeeRequest;
import com.demo.PoS.dto.employee.EmployeeResponse;
import com.demo.PoS.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse createdEmployee = employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable UUID employeeId) {
        EmployeeResponse employee = employeeService.findEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable UUID employeeId, @Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(employeeId, employeeRequest);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
