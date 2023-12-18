package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
