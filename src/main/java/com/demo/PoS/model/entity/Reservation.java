package com.demo.PoS.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Order order;

    @OneToOne
    private ServiceSlot serviceSlot;

    @Column(length = 1024)
    private String description;

    @Embedded
    @Builder.Default
    private PosTimestamps posTimestamps = new PosTimestamps();

}
