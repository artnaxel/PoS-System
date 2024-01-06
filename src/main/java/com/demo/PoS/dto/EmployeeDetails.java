package com.demo.PoS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDetails {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

}
