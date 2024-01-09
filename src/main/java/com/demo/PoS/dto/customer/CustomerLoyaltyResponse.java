package com.demo.PoS.dto.customer;

import com.demo.PoS.model.entity.LoyaltyProgram;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerLoyaltyResponse {

    private UUID id;
    private String name;
    private String surname;
    private LoyaltyProgram loyaltyProgram;
}
