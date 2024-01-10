package com.demo.PoS.mappers;

import com.demo.PoS.dto.order.OrderProductDto;
import com.demo.PoS.model.relationship.OrderProduct;

public class OrderProductMapper {
    public static OrderProductDto toDto(OrderProduct orderProduct) {
        return new OrderProductDto(
                orderProduct.getProduct().getId(),
                orderProduct.getProduct().getPrice(),
                orderProduct.getProduct().getDiscount() != null ? orderProduct.getProduct().getDiscount().getId() : null,
                orderProduct.getProduct().getLoyaltyProgram() != null ? orderProduct.getProduct().getLoyaltyProgram().getId() : null,
                orderProduct.getCount()
        );
    }
}
