package com.demo.PoS.dto.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReservationResponseDto(
        UUID reservationId,
        UUID orderId,
        UUID serviceId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime creationTime,
        LocalDateTime lastUpdateTime
) {
}
