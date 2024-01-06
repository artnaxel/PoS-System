package com.demo.PoS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeDto {

    private UUID id;
    private String name;
    private String surname;
}
