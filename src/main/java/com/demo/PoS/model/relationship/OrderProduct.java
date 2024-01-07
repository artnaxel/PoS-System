package com.demo.PoS.model.relationship;

import com.demo.PoS.dto.OrderProductDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Mutability;

import java.util.UUID;
@Data
@Entity
@Table(name = "pos_order_product")
public class OrderProduct {
    @EmbeddedId
    private final OrderProductKey id;

    public OrderProduct() {
        id = new OrderProductKey();
    }

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int count = 1;

    public OrderProductDto toDto() {
        return new OrderProductDto(
                this.product.getId(),
                this.count
        );
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductKey {
        @Column(name = "order_id")
        private UUID orderId;

        @Column(name = "product_id")
        private UUID productId;
    }
}