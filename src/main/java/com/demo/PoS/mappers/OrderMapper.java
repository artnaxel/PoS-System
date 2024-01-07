package com.demo.PoS.mappers;

import com.demo.PoS.dto.order.OrderDiscountDto;
import com.demo.PoS.dto.order.OrderDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.relationship.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .employeeId(order.getEmployee().getId())
                .orderProducts(order.getOrderProducts().stream().map(OrderProduct::toDto).toList())
                .tippingAmount(order.getTippingAmount())
                .status(order.getOrderStatus())
                .build();
    }

    public OrderDiscountDto toDiscountDto(Order order) {
        return OrderDiscountDto.builder()
                .orderId(order.getId())
                .discountType(order.getDiscountType())
                .discountAmount(order.getDiscountAmount())
                .build();
    }
}
