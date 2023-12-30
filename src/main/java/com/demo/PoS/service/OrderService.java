package com.demo.PoS.service;

import com.demo.PoS.dto.OrderDto;
import com.demo.PoS.dto.OrderProductDto;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.model.relationship.OrderProduct;
import com.demo.PoS.repository.CustomerRepository;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.ReceiptRepository;
import com.demo.PoS.repository.relations.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void createOrder(OrderDto dto) {
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow();
        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow();
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .customer(customer)
                .employee(employee)
                .build();
        orderRepository.save(order);
        attachProducts(order, dto.orderProducts());
    }

    @Transactional
    public void editOrder(UUID id, OrderDto dto) {
        if (!id.equals(dto.id()))
            throw new IllegalArgumentException("Cannot change the ID of an order");
        Order order = orderRepository.findById(id).orElseThrow();
        Employee employee = dto.employeeId() == null ? order.getEmployee() : employeeRepository.findById(dto.employeeId()).orElseThrow();
        Customer customer = dto.customerId() == null ? order.getCustomer() : customerRepository.findById(dto.customerId()).orElseThrow();
        order.setCustomer(customer);
        order.setEmployee(employee);
        order.setTippingAmount(dto.tippingAmount());
        order.setOrderStatus(dto.status());

        Set<OrderProduct> orderProducts = dto.orderProducts().stream()
                .map(it -> {
                    OrderProduct op = new OrderProduct();
                    op.getId().setProductId(it.productId());
                    op.getId().setOrderId(order.getId());
                    op.setCount(it.count());
                    return op;
                }).collect(Collectors.toSet());
        var dtoIds = orderProducts.stream().map(OrderProduct::getId).collect(Collectors.toSet());
        var removedOrderProducts = order.getOrderProducts().stream()
                .map(OrderProduct::getId)
                .filter(it -> !dtoIds.contains(it)).toList();
        var addedOrderProducts = orderProducts.stream().filter(it -> !order.getOrderProducts().contains(it)).collect(Collectors.toSet());
        orderProductRepository.deleteAllById(removedOrderProducts);
        orderProductRepository.saveAll(addedOrderProducts);
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
    }

    @Transactional
    public Receipt generateReceipt(Order order) {
        if (order.getReceipt() != null)
            return order.getReceipt();

        String template = """
                Receipt for Order %s
                Discount:         %s
                """;
        Receipt r = Receipt.builder()
                .order(order)
                .text(String.format(template, order.getId(), order.getDiscountAmount()))
                .build();
        order.setOrderStatus(OrderStatus.COMPLETED); // should have separate method for completing the order
        orderRepository.save(order);
        receiptRepository.save(r);

        return r;
    }

    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public Receipt generateReceipt(UUID orderId) {
        return generateReceipt(findOrder(orderId));
    }

    private void attachProducts(Order order, List<OrderProductDto> productDtos) {
        for (OrderProductDto productDto : productDtos) {
            Product p = Product.builder()
                    .id(productDto.productId())
                    .build();
            orderProductRepository.save(order.addProduct(p, productDto.count()));
        }
    }
}
