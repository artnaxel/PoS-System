package com.demo.PoS.service;

import com.demo.PoS.dto.OrderDiscountDto;
import com.demo.PoS.dto.OrderDto;
import com.demo.PoS.dto.OrderProductDto;
import com.demo.PoS.dto.ReceiptDto;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final OrderProductRepository orderProductRepository;

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
    public Order createOrder(OrderDto dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .customer(customer)
                .build();
        Order createdOrder = orderRepository.save(order);
        attachProducts(order, dto.orderProducts());
        return createdOrder;
    }

    @Transactional
    public void editOrder(UUID id, OrderDto dto) {
        if (!id.equals(dto.id())) {
            throw new IllegalArgumentException("Cannot change the ID of an order");
        }

        Order order = orderRepository.findOrderWithProductsAndServicesById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        Customer customer = dto.customerId() == null ? order.getCustomer() : customerRepository
                .findById(dto.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        for (OrderProductDto updatedProduct : Optional.ofNullable(dto.orderProducts()).orElse(Collections.emptyList())) {
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
                .tippingAmount(dto.tippingAmount())
                .orderStatus(dto.status())
                .build();

        orderRepository.save(updatedOrder);
    }

    @Transactional
    public ReceiptDto generateReceipt(Order order) {
        if (order.getReceipt() != null) {
            return ReceiptMapper.toDto(order.getReceipt());
        }

        String template = """
                Receipt for Order [%s]
                Product Costs   %s EUR
                Service Costs   %s EUR
                Discounts       %s EUR
                -----------------------
                Total           %s EUR
                """;

        Function<OrderProduct, BigDecimal> orderProductCost = (OrderProduct orderProduct) ->
                orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProduct.getCount()));

        BigDecimal productCosts = Optional.of(order.getOrderProducts().stream()
                        .map(orderProductCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .orElse(BigDecimal.ZERO);

        BigDecimal serviceCosts = Optional.of(getServices(order).stream()
                        .map(Item::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .orElse(BigDecimal.ZERO);

        BigDecimal discounts = order.getOrderProducts().stream()
                .map(it -> Optional.ofNullable(it.getProduct().getDiscount())
                        .orElse(Discount.builder().discountRate(BigDecimal.ZERO).build())
                        .getDiscountRate()
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                        .multiply(orderProductCost.apply(it)))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(getServices(order).stream()
                        .map(it -> Optional.ofNullable(it.getDiscount())
                                .orElse(Discount.builder().discountRate(BigDecimal.ZERO).build())
                                .getDiscountRate()
                                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                .multiply(it.getPrice()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                );

        Receipt receipt = Receipt.builder()
                .order(order)
                .text(String.format(template,
                        order.getId(),
                        productCosts,
                        serviceCosts,
                        discounts,
                        productCosts
                                .add(serviceCosts)
                                .subtract(discounts)
                ))
                .build();
        order.setOrderStatus(OrderStatus.COMPLETED); // should have separate method for completing the order
        orderRepository.save(order);
        receiptRepository.save(receipt);

        return ReceiptMapper.toDto(receipt);
    }

    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
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

    private List<ProvidedService> getServices(Order order) {
        return order.getReservations().stream()
                .map(it -> it.getServiceSlot().getProvidedService())
                .toList();
    }
}
