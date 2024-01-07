package com.demo.PoS.model.entity;

import com.demo.PoS.model.enums.PaymentMethod;
import com.demo.PoS.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "Payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDateTime;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = true)
    private BigDecimal refundAmount;

    @Column(nullable = true)
    private Date refundDate;

}
