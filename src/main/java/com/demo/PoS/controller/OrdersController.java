package com.demo.PoS.controller;

import com.demo.PoS.dto.OrderDiscountDto;
import com.demo.PoS.dto.OrderDto;
import com.demo.PoS.dto.ReceiptDto;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<List<OrderDto>> listOrders() {
        return new ResponseEntity<>(
                orderService.findAllOrders().stream().map(Order::toOrderDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderDto> getOrder(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.findOrder(orderId).toOrderDto(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}/discount")
    ResponseEntity<OrderDiscountDto> getOrderDiscount(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.findOrder(orderId).toOrderDiscountDto(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}/receipt")
    ResponseEntity<ReceiptDto> getOrderReceipt(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.generateReceipt(orderId).toReceiptDto(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> createOrder() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    ResponseEntity<String> editOrder() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    ResponseEntity<String> cancelOrder() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
