package com.demo.PoS.mappers;

import com.demo.PoS.dto.ReceiptDto;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.model.relationship.OrderProduct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ReceiptMapper {
    private final TemplateEngine templateEngine;

    public static ReceiptDto toDto(Receipt receipt) {
        return ReceiptDto.builder()
                .orderId(receipt.getOrder().getId())
                .receiptString(receipt.getText())
                .build();
    }

    public String mapOrderToReceipt(Order order) {
        Context context = new Context();

        List<ProductTemplate> productList = new ArrayList<>();
        List<ServiceTemplate> serviceList = new ArrayList<>();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            BigDecimal price = orderProduct.getProduct().getPrice();
            BigDecimal discount = Optional.ofNullable(orderProduct.getProduct().getDiscount())
                    .orElse(Discount.builder().discountRate(BigDecimal.ZERO).build())
                    .getDiscountRate()
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                    .multiply(price);
            BigDecimal totalPrice = price
                    .subtract(discount)
                    .multiply(BigDecimal.valueOf(orderProduct.getCount()));
            productList.add(ProductTemplate.builder()
                    .name(orderProduct.getProduct().getName())
                    .count(orderProduct.getCount())
                    .price(price)
                    .discount(discount)
                    .totalPrice(totalPrice)
                    .build());
        }

        for (Reservation reservation : order.getReservations()) {
            ServiceSlot slot = reservation.getServiceSlot();
            ProvidedService service = slot.getProvidedService();
            BigDecimal price = service.getPrice();
            BigDecimal discount = Optional.ofNullable(service.getDiscount())
                    .orElse(Discount.builder().discountRate(BigDecimal.ZERO).build())
                    .getDiscountRate()
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                    .multiply(price);
            BigDecimal totalPrice = price
                    .subtract(discount);
            serviceList.add(ServiceTemplate.builder()
                    .name("")
                    .startTime(slot.getStartTime())
                    .endTime(slot.getEndTime())
                    .price(price)
                    .discount(discount)
                    .totalPrice(totalPrice)
                    .build());
        }
        context.setVariable("order", OrderTemplate.builder()
                .id(order.getId())
                .products(productList)
                .services(serviceList)
                .build());

        return templateEngine.process("receipt.html", context);
    }

    @Builder
    private record ProductTemplate(
            String name,
            Integer count,
            BigDecimal price,
            BigDecimal discount,
            BigDecimal totalPrice
    ) {
    }

    @Builder
    private record ServiceTemplate(
            String name,
            LocalDateTime startTime,
            LocalDateTime endTime,
            BigDecimal price,
            BigDecimal discount,
            BigDecimal totalPrice
    ) {
    }

    @Builder
    private record OrderTemplate(
            UUID id,
            List<ProductTemplate> products,
            List<ServiceTemplate> services
    ) {
    }
}
