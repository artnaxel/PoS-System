package com.demo.PoS.mappers;

import com.demo.PoS.dto.providedService.ProvidedServiceResponse;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.ProvidedService;

import java.util.Optional;

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
                .build();
    }
}
