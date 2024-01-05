package com.demo.PoS.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RestockRequest {
    @NotNull
    @Min(value = 0)
    private Integer stock;
}

