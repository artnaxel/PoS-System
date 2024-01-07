package com.demo.PoS.mappers;

import com.demo.PoS.dto.reservation.ReservationResponseDto;
import com.demo.PoS.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationResponseDto toDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationId(reservation.getId())
                .orderId(reservation.getOrder().getId())
                .startTime(reservation.getServiceSlot().getStartTime())
                .endTime(reservation.getServiceSlot().getEndTime())
                .creationTime(reservation.getPosTimestamps().getCreatedAt().toLocalDateTime())
                .lastUpdateTime(reservation.getPosTimestamps().getModifiedAt().toLocalDateTime())
                .build();
    }
}
