package com.demo.PoS.dto.employee;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeResponse {

    private UUID id;
    private String name;
    private String surname;
}
