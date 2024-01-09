package com.demo.PoS.mappers;

import com.demo.PoS.dto.customer.CustomerLoyaltyResponse;
import com.demo.PoS.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public static CustomerLoyaltyResponse toCustomerLoyaltyResponse(Customer customer) {
        return CustomerLoyaltyResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .loyaltyProgram(customer.getLoyaltyProgram())
                .build();
    }
}
