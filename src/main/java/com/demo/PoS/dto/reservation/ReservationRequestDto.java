package com.demo.PoS.dto.reservation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReservationRequestDto(
        @Length(max = 1024)
        String description,
        @NotNull
        UUID serviceId,
        @NotNull
        UUID orderId,
        @FutureOrPresent
        LocalDateTime startTime,
        @FutureOrPresent
        LocalDateTime endTime
) {
}
