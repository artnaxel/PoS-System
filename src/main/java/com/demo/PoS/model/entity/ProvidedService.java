package com.demo.PoS.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProvidedService extends Item {

    @OneToMany(mappedBy = "providedService")
    private Set<ServiceSlot> serviceSlots;

    @ManyToOne
    private Discount discount;

    @ManyToOne
    private LoyaltyProgram loyaltyProgram;
}
