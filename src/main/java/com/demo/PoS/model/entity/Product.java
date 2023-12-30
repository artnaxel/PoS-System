package com.demo.PoS.model.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Item {

    private Integer stock;

    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();

}

