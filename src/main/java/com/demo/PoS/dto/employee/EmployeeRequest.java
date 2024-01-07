package com.demo.PoS.dto.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

}
