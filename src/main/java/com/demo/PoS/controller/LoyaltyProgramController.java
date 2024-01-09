package com.demo.PoS.controller;

import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramRequest;
import com.demo.PoS.dto.loyaltyProgram.LoyaltyProgramResponse;
import com.demo.PoS.service.CustomerService;
import com.demo.PoS.service.LoyaltyProgramService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/loyaltyPrograms")
public class LoyaltyProgramController {
    private final LoyaltyProgramService loyaltyProgramService;
    private final CustomerService customerService;

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

    @PostMapping("/{loyaltyProgramId}/{customerId}")
    public ResponseEntity<Void> assignLoyaltyProgram(
            @PathVariable UUID customerId,
            @PathVariable UUID loyaltyProgramId) {
        customerService.assignLoyaltyProgram(customerId, loyaltyProgramId);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{customerId}/removeLoyaltyProgram")
    public ResponseEntity<Void> removeLoyaltyProgram(@PathVariable UUID customerId) {
        customerService.removeLoyaltyProgram(customerId);
        return ResponseEntity.ok().build();
    }

}
