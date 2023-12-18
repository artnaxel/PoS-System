package com.demo.PoS.model.entity;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@MappedSuperclass
@Setter
public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;
}