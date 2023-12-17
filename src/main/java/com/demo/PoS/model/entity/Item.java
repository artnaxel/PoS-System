package com.demo.PoS.model.entity;
import com.demo.PoS.model.enums.ItemType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "pos_item")
public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Integer stock;
}
