package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "loyalty_program_id")
    private LoyaltyProgram loyaltyProgram;
}
