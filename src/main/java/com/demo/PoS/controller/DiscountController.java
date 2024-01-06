package com.demo.PoS.controller;

import com.demo.PoS.dto.discount.DiscountRequest;
import com.demo.PoS.dto.discount.DiscountResponse;
import com.demo.PoS.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseEntity<DiscountResponse> createDiscount(@RequestBody DiscountRequest discount) {
        DiscountResponse created = discountService.createDiscount(discount);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountResponse> getDiscount(@PathVariable UUID discountId) {
        DiscountResponse discount = discountService.getDiscount(discountId);
        return ResponseEntity.ok(discount);
    }

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        List<DiscountResponse> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable UUID discountId, @RequestBody DiscountRequest discount) {
        DiscountResponse updated = discountService.updateDiscount(discountId, discount);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable UUID discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}

