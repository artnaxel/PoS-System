package com.demo.PoS.service;

import com.demo.PoS.dto.order.OrderDiscountDto;
import com.demo.PoS.dto.order.OrderDto;
import com.demo.PoS.dto.order.OrderProductDto;
import com.demo.PoS.dto.receipt.ReceiptDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.OrderMapper;
import com.demo.PoS.mappers.ReceiptMapper;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.model.relationship.OrderProduct;
import com.demo.PoS.repository.CustomerRepository;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.ReceiptRepository;
import com.demo.PoS.repository.relations.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final OrderProductRepository orderProductRepository;
    private final ReceiptMapper receiptMapper;

    @Transactional
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrder(UUID id) {
        return orderRepository.findOrderWithProductsAndServicesById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public List<OrderDto> getAllOrders() {
        return findAllOrders().stream().map(OrderMapper::toDto).toList();
    }

    public OrderDto getOrder(UUID id) {
        return OrderMapper.toDto(findOrder(id));
    }

    public OrderDiscountDto getOrderDiscount(UUID id) {
        return OrderMapper.toDiscountDto(findOrder(id));
    }

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .customer(customer)
                .build();
        Order createdOrder = orderRepository.save(order);
        attachProducts(order, orderDto.orderProducts());
        return createdOrder;
    }

    @Transactional
    public void editOrder(UUID id, OrderDto orderDto) {
        if (!id.equals(orderDto.id())) {
            throw new IllegalArgumentException("Cannot change the ID of an order");
        }

        Order order = orderRepository.findOrderWithProductsAndServicesById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        Customer customer = orderDto.customerId() == null ? order.getCustomer() : customerRepository
                .findById(orderDto.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        for (OrderProductDto updatedProduct : Optional.ofNullable(orderDto.orderProducts())
                .orElse(Collections.emptyList())) {
            Optional<OrderProduct> orderProduct = order.getOrderProducts().stream()
                    .filter(it -> it.getId().getProductId().equals(updatedProduct.productId()))
                    .findFirst();
            if (orderProduct.isEmpty()) {
                orderProductRepository.save(addProduct(order, Product.builder()
                        .id(updatedProduct.productId())
                        .build(), updatedProduct.count()));
            } else {
                orderProduct.get().setCount(updatedProduct.count());
                orderProductRepository.save(orderProduct.get());
            }
        }

        Order updatedOrder = order.toBuilder()
                .customer(customer)
                .tippingAmount(orderDto.tippingAmount())
                .orderStatus(orderDto.status())
                .build();

        orderRepository.save(updatedOrder);
    }

    @Transactional
    public ReceiptDto generateReceipt(Order order) {
        if (order.getReceipt() != null) {
            return ReceiptMapper.toDto(order.getReceipt());
        }

        Receipt receipt = Receipt.builder()
                .order(order)
                .text(receiptMapper.mapOrderToReceipt(order))
                .build();
        order.setOrderStatus(OrderStatus.COMPLETED); // should have separate method for completing the order
        orderRepository.save(order);
        receiptRepository.save(receipt);

        return ReceiptMapper.toDto(receipt);
    }

    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public ReceiptDto generateReceipt(UUID orderId) {
        Order order = orderRepository.findOrderWithProductsAndServicesAndDiscountsById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        return generateReceipt(order);
    }

    private void attachProducts(Order order, List<OrderProductDto> productDtos) {
        for (OrderProductDto productDto : productDtos) {
            Product product = Product.builder()
                    .id(productDto.productId())
                    .build();
            orderProductRepository.save(addProduct(order, product, productDto.count()));
        }
    }

    public OrderProduct addProduct(Order order, Product product, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setCount(count);
        return orderProduct;
    }

}
