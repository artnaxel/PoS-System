package com.demo.PoS.dto;

import com.demo.PoS.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDto (
        UUID id,
        UUID customerId,
        UUID employeeId,
        List<OrderProductDto> orderProducts,
        BigDecimal tippingAmount,
        OrderStatus status
){}
