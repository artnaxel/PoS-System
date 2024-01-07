package com.demo.PoS.mappers;

import com.demo.PoS.dto.employee.EmployeeResponse;
import com.demo.PoS.model.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public static EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build();
    }
}
