package com.demo.PoS.controller;

import com.demo.PoS.dto.EmployeeDetails;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID employeeId, @RequestBody EmployeeDetails employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
