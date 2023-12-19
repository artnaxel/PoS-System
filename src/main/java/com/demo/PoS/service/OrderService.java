package com.demo.PoS.service;

import com.demo.PoS.dto.ReceiptDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.entity.Receipt;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;
    @Transactional
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
    public Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Receipt generateReceipt(Order order) {
        if(order.getReceipt() != null)
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
    public Receipt generateReceipt(UUID orderId) {
        return generateReceipt(findOrder(orderId));
    }
}
