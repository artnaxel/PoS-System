package com.demo.PoS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class EmployeeDetails {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

}
