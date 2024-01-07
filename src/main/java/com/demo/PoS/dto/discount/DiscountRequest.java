package com.demo.PoS.dto.discount;

import com.demo.PoS.model.enums.DiscountStatus;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountRequest {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal discountRate;

    @NotNull
    @FutureOrPresent
    private LocalDateTime validFrom;

    @NotNull
    @FutureOrPresent
    private LocalDateTime validUntil;

    @NotNull
    private DiscountStatus discountStatus;
}
