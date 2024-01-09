package com.demo.PoS.mappers;

import com.demo.PoS.dto.providedService.ProvidedServiceResponse;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.ProvidedService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProvidedServiceMapper {
    public static ProvidedServiceResponse toProvidedServiceResponse(ProvidedService providedService) {
        return ProvidedServiceResponse.builder()
                .id(providedService.getId())
                .name(providedService.getName())
                .description(providedService.getDescription())
                .price(providedService.getPrice())
                .discountId(Optional.ofNullable(providedService.getDiscount())
                        .map(Discount::getId)
                        .orElse(null))
                .loyaltyDiscountId(Optional.ofNullable(providedService.getLoyaltyProgram())
                        .map(LoyaltyProgram::getId)
                        .orElse(null))
                .build();
    }
}
