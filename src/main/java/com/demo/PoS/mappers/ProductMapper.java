package com.demo.PoS.mappers;

import com.demo.PoS.dto.product.ProductResponse;
import com.demo.PoS.model.entity.Discount;
import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapper {
    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discountId(Optional.ofNullable(product.getDiscount())
                        .map(Discount::getId)
                        .orElse(null))
                .loyaltyProgramId(Optional.ofNullable(product.getLoyaltyProgram())
                        .map(LoyaltyProgram::getId)
                        .orElse(null))
                .build();
    }
}
