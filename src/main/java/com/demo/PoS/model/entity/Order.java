package com.demo.PoS.model.entity;

import com.demo.PoS.dto.OrderDiscountDto;
import com.demo.PoS.dto.OrderDto;
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
@Builder
@Entity
@Table(name = "pos_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    public OrderProduct addProduct(Product product, int count) {
        var op = new OrderProduct();
        op.setOrder(this);
        op.setProduct(product);
        op.setCount(count);
        return op;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private BigDecimal discountAmount;

    private BigDecimal tippingAmount;

    @OneToOne(mappedBy = "order")
    private Receipt receipt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    @Builder.Default
    private PosTimestamps timestamps = new PosTimestamps();

    public OrderDto toOrderDto() {
        return new OrderDto(this.id,
                this.customer.getId(),
                this.employee.getId(),
                this.getOrderProducts().stream().map(OrderProduct::toDto).toList(),
                this.tippingAmount,
                this.orderStatus);
    }

    public OrderDiscountDto toOrderDiscountDto() {
        return new OrderDiscountDto(
                this.id,
                this.discountType,
                this.discountAmount
        );
    }
}
