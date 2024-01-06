package com.demo.PoS.controller;

import com.demo.PoS.dto.DiscountDetails;
import com.demo.PoS.dto.DiscountDto;
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
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDetails discount) {
        DiscountDto created = discountService.createDiscount(discount);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable UUID discountId) {
        DiscountDto discount = discountService.getDiscount(discountId);
        return ResponseEntity.ok(discount);
    }

    @GetMapping
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {
        List<DiscountDto> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountDto> updateDiscount(@PathVariable UUID discountId, @RequestBody DiscountDetails discount) {
        DiscountDto updated = discountService.updateDiscount(discountId, discount);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable UUID discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}

