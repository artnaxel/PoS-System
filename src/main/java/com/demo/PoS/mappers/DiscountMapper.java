package com.demo.PoS.mappers;

import com.demo.PoS.dto.discount.DiscountResponse;
import com.demo.PoS.model.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {
    public static DiscountResponse toDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .name(discount.getName())
                .discountRate(discount.getDiscountRate())
                .validFrom(discount.getValidFrom())
                .validUntil(discount.getValidUntil())
                .discountStatus(discount.getDiscountStatus())
                .build();
    }
}
