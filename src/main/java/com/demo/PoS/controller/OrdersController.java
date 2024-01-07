package com.demo.PoS.controller;

import com.demo.PoS.dto.order.OrderDiscountDto;
import com.demo.PoS.dto.order.OrderDto;
import com.demo.PoS.dto.receipt.ReceiptDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrdersController {
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<List<OrderDto>> listOrders() {
        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderDto> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/{orderId}/discount")
    ResponseEntity<OrderDiscountDto> getOrderDiscount(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderDiscount(orderId));
    }

    @GetMapping("/{orderId}/receipt")
    ResponseEntity<ReceiptDto> getOrderReceipt(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.generateReceipt(orderId));
    }

    @PostMapping
    ResponseEntity<UUID> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(orderDto);
        return ResponseEntity.ok(order.getId());
    }

    @PutMapping("/{orderId}")
    ResponseEntity<Void> editOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderDto dto) {
        orderService.editOrder(orderId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/cancel")
    ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
