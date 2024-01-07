package com.demo.PoS.model.entity;

import com.demo.PoS.model.enums.DiscountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private BigDecimal discountRate;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private DiscountStatus discountStatus;

}

