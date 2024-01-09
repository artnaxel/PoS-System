package com.demo.PoS.mappers;

import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramResponse;
import com.demo.PoS.model.entity.LoyaltyProgram;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyProgramMapper {
    public static LoyaltyProgramResponse toLoyaltyProgramResponse(LoyaltyProgram loyaltyProgram) {
        return LoyaltyProgramResponse.builder()
                .id(loyaltyProgram.getId())
                .name(loyaltyProgram.getName())
                .discountRate(loyaltyProgram.getDiscountRate())
                .build();
    }
}
