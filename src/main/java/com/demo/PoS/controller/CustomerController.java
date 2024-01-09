package com.demo.PoS.controller;

import com.demo.PoS.dto.customer.CustomerLoyaltyResponse;
import com.demo.PoS.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerLoyaltyResponse>> getAllCustomers() {
        List<CustomerLoyaltyResponse> customerLoyaltyResponseList = customerService.getAllCustomers();
        return ResponseEntity.ok(customerLoyaltyResponseList);
    }

}
