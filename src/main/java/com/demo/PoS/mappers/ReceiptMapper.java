package com.demo.PoS.mappers;

import com.demo.PoS.dto.receipt.ReceiptDto;
import com.demo.PoS.model.entity.Receipt;
import com.demo.PoS.model.entity.*;
import com.demo.PoS.model.enums.DiscountType;
import com.demo.PoS.model.relationship.OrderProduct;
import com.demo.PoS.service.LoyaltyProgramService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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
    private final LoyaltyProgramService loyaltyProgramService;

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
        List<LoyaltyDiscountTemplate> loyaltyDiscountList = new ArrayList<>();

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
                    .name(service.getName())
                    .startTime(slot.getStartTime())
                    .endTime(slot.getEndTime())
                    .price(price)
                    .discount(discount)
                    .totalPrice(totalPrice)
                    .build());
        }

        for (Pair<OrderProduct, LoyaltyProgram> discount : loyaltyProgramService.getLoyaltyProgramsAndProductsByOrder(order.getId())) {
            BigDecimal discountForOneProduct = discount.getFirst().getProduct().getPrice()
                    .multiply(Optional.ofNullable(discount.getSecond().getDiscountRate())
                            .orElse(BigDecimal.ZERO));
            loyaltyDiscountList.add(
                    LoyaltyDiscountTemplate.builder()
                            .productName(discount.getFirst().getProduct().getName())
                            .programName(discount.getSecond().getName())
                            .amount(discountForOneProduct.multiply(BigDecimal.valueOf(discount.getFirst().getCount())))
                            .build()
            );
        }

        BigDecimal totalOrderPrice =
                productList.stream()
                        .map(ProductTemplate::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .add(
                                serviceList.stream()
                                        .map(ServiceTemplate::totalPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        );
        totalOrderPrice = totalOrderPrice
                .subtract(order.getDiscountType() == DiscountType.FLAT
                        ? Optional.ofNullable(order.getDiscountAmount())
                        .orElse(BigDecimal.ZERO)
                        : totalOrderPrice.multiply(Optional.ofNullable(order.getDiscountAmount())
                        .orElse(BigDecimal.ZERO)));
        totalOrderPrice = totalOrderPrice
                .subtract(loyaltyDiscountList.stream()
                        .map(LoyaltyDiscountTemplate::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        totalOrderPrice = totalOrderPrice
                .add(totalOrderPrice.multiply(BigDecimal.valueOf(0.21)))
                .add(Optional.ofNullable(order.getTippingAmount())
                        .orElse(BigDecimal.ZERO));

        context.setVariable("order", OrderTemplate.builder()
                .id(order.getId())
                .products(productList)
                .services(serviceList)
                .loyaltyDiscounts(loyaltyDiscountList)
                .taxRate(BigDecimal.valueOf(21L))
                .discount(order.getDiscountAmount())
                .tip(order.getTippingAmount())
                .totalPrice(totalOrderPrice.setScale(2, RoundingMode.HALF_UP))
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
    private record LoyaltyDiscountTemplate(
            String programName,
            String productName,
            BigDecimal amount
    ) {
    }

    @Builder
    private record OrderTemplate(
            UUID id,
            List<ProductTemplate> products,
            List<ServiceTemplate> services,
            List<LoyaltyDiscountTemplate> loyaltyDiscounts,
            BigDecimal taxRate,
            BigDecimal tip,
            BigDecimal discount,
            BigDecimal totalPrice
    ) {
    }
}
