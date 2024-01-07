package com.demo.PoS.controller;

import com.demo.PoS.dto.reservation.ReservationRequestDto;
import com.demo.PoS.dto.reservation.ReservationResponseDto;
import com.demo.PoS.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@Validated
@RequiredArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getAllReservations(@PathVariable UUID reservationId) {
        return ResponseEntity.ok(reservationService.getReservation(reservationId));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody @Valid ReservationRequestDto reservationResponseDto) {
        return ResponseEntity.ok(reservationService.createReservation(reservationResponseDto));
    }

    @DeleteMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable UUID reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

}
