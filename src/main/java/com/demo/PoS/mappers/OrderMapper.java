package com.demo.PoS.mappers;

import com.demo.PoS.dto.order.OrderDiscountDto;
import com.demo.PoS.dto.order.OrderDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.relationship.OrderProduct;

public class OrderMapper {
    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .orderProducts(order.getOrderProducts().stream().map(OrderProductMapper::toDto).toList())
                .services(order.getReservations().stream()
                        .map(it -> ProvidedServiceMapper.toProvidedServiceResponse(
                                it.getServiceSlot().getProvidedService()))
                        .toList())
                .tippingAmount(order.getTippingAmount())
                .status(order.getOrderStatus())
                .build();
    }

    public static OrderDiscountDto toDiscountDto(Order order) {
        return OrderDiscountDto.builder()
                .orderId(order.getId())
                .discountType(order.getDiscountType())
                .discountAmount(order.getDiscountAmount())
                .build();
    }
}
