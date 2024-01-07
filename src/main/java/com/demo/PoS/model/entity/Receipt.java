package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @GeneratedValue
    @Id
    private UUID id;
    private String text;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @Embedded
    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();
}
