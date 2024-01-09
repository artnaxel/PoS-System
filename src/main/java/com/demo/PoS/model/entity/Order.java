package com.demo.PoS.model.entity;

import com.demo.PoS.model.enums.DiscountType;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.model.relationship.OrderProduct;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "pos_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "order")
    private Set<Reservation> reservations;

    private DiscountType discountType;

    private BigDecimal discountAmount;

    private BigDecimal tippingAmount;

    @OneToOne(mappedBy = "order")
    private Receipt receipt;

    private OrderStatus orderStatus;

    @Embedded
    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();

}
