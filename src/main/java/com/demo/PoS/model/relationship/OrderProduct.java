package com.demo.PoS.model.relationship;

import com.demo.PoS.dto.order.OrderProductDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    private Integer count = 1;

    public OrderProductDto toDto() {
        return new OrderProductDto(
                this.product.getId(),
                this.product.getDiscount() != null ? this.product.getDiscount().getDiscountRate() : null,
                this.count
        );
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductKey implements Serializable {
        @Column(name = "order_id")
        private UUID orderId;

        @Column(name = "product_id")
        private UUID productId;
    }
}
