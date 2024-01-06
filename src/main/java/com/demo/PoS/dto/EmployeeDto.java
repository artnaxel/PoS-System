package com.demo.PoS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeDto {

    @NotBlank
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

}
