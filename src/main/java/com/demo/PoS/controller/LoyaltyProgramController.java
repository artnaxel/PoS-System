package com.demo.PoS.controller;

import com.demo.PoS.dto.discount.DiscountResponse;
import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramRequest;
import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramResponse;
import com.demo.PoS.service.LoyaltyProgramService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/loyaltyPrograms")
public class LoyaltyProgramController {
    private final LoyaltyProgramService loyaltyProgramService;


    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService) {
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @PostMapping
    public ResponseEntity<LoyaltyProgramResponse> createLoyaltyProgram(@RequestBody @Valid LoyaltyProgramRequest loyaltyProgram) {
        LoyaltyProgramResponse created = loyaltyProgramService.createLoyaltyProgram(loyaltyProgram);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LoyaltyProgramResponse>> getAllLoyaltyPrograms() {
        List<LoyaltyProgramResponse> loyaltyProgramList = loyaltyProgramService.getAllLoyaltyPrograms();
        return ResponseEntity.ok(loyaltyProgramList);
    }

    @GetMapping("/{loyaltyProgramId}")
    public ResponseEntity<LoyaltyProgramResponse> getLoyaltyProgram(@PathVariable UUID loyaltyProgramId) {
        LoyaltyProgramResponse loyaltyProgram = loyaltyProgramService.getLoyaltyProgram(loyaltyProgramId);
        return ResponseEntity.ok(loyaltyProgram);
    }

    @PutMapping("/{loyaltyProgramId}")
    public ResponseEntity<LoyaltyProgramResponse> updateLoyaltyProgram(@PathVariable UUID loyaltyProgramId, @RequestBody @Valid LoyaltyProgramRequest loyaltyProgram) {
        LoyaltyProgramResponse updated = loyaltyProgramService.updateLoyaltyProgram(loyaltyProgramId, loyaltyProgram);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{loyaltyProgramId}")
    public ResponseEntity<Void> deleteLoyaltyProgram(@PathVariable UUID loyaltyProgramId) {
        loyaltyProgramService.deleteLoyaltyProgram(loyaltyProgramId);
        return ResponseEntity.noContent().build();
    }


}
