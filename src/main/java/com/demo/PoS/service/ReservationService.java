package com.demo.PoS.service;

import com.demo.PoS.dto.reservation.ReservationRequestDto;
import com.demo.PoS.dto.reservation.ReservationResponseDto;
import com.demo.PoS.exceptions.NotFoundException;
import com.demo.PoS.mappers.ReservationMapper;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.entity.ProvidedService;
import com.demo.PoS.model.entity.Reservation;
import com.demo.PoS.model.entity.ServiceSlot;
import com.demo.PoS.model.enums.ServiceSlotStatus;
import com.demo.PoS.repository.ProvidedServiceRepository;
import com.demo.PoS.repository.ReservationRepository;
import com.demo.PoS.repository.ServiceSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final OrderService orderService;
    private final ServiceSlotService serviceSlotService;
    private final ServiceSlotRepository serviceSlotRepository;

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation findReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
    }

    public List<ReservationResponseDto> getReservations() {
        return findReservations().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public ReservationResponseDto getReservation(UUID reservationId) {
        return reservationMapper.toDto(findReservationById(reservationId));
    }

    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto) {
        Order order = orderService.findOrder(reservationRequestDto.orderId());
        List<ServiceSlot> availableSlots = serviceSlotService.findAvailableSlotsByService(reservationRequestDto.serviceId()).stream()
                .filter(it -> it.getServiceSlotStatus() == ServiceSlotStatus.FREE)
                .toList();
        ServiceSlot slot = selectServiceSlot(reservationRequestDto.startTime(), reservationRequestDto.endTime(), availableSlots)
                .orElseThrow(() -> new IllegalArgumentException("No service slots available"));
        slot.setServiceSlotStatus(ServiceSlotStatus.RESERVED);
        serviceSlotRepository.save(slot);

        Reservation reservation = Reservation.builder()
                .serviceSlot(slot)
                .order(order)
                .description(reservationRequestDto.description())
                .build();
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }

    @Transactional
    public void editReservation(UUID reservationId, ReservationRequestDto reservationRequestDto) {
        Reservation  reservation = findReservationById(reservationId);
        Order order = orderService.findOrder(reservationRequestDto.orderId());
        List<ServiceSlot> availableSlots = serviceSlotService.findAvailableSlotsByService(reservationRequestDto.serviceId()).stream()
                .filter(it -> it.getServiceSlotStatus() == ServiceSlotStatus.FREE)
                .collect(Collectors.toList());
        availableSlots.add(reservation.getServiceSlot());

        ServiceSlot slot = selectServiceSlot(reservationRequestDto.startTime(), reservationRequestDto.endTime(), availableSlots)
                .orElseThrow(() -> new IllegalArgumentException("No service slots available"));
        if(!slot.equals(reservation.getServiceSlot())) {
            ServiceSlot previousSlot = reservation.getServiceSlot();
            previousSlot.setServiceSlotStatus(ServiceSlotStatus.FREE);
            slot.setServiceSlotStatus(ServiceSlotStatus.RESERVED);
            reservation.setServiceSlot(slot);
            serviceSlotRepository.saveAll(List.of(previousSlot, slot));
        }

        reservation.setOrder(order);
        reservation.setDescription(reservationRequestDto.description());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findByIdWithSlot(reservationId).orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservation.getServiceSlot().setServiceSlotStatus(ServiceSlotStatus.FREE);
        serviceSlotRepository.save(reservation.getServiceSlot());

        reservationRepository.delete(reservation);
    }

    private Optional<ServiceSlot> selectServiceSlot(LocalDateTime startTime, LocalDateTime endTime, List<ServiceSlot> availableSlots) {
        Duration requestedDuration = Duration.between(startTime, endTime);
        Predicate<ServiceSlot> slotStartCompatible = slot -> !slot.getStartTime().isAfter(startTime);
        Predicate<ServiceSlot> slotLongEnough = slot -> !slot.getStartTime().plus(requestedDuration).isAfter(slot.getEndTime());

        return availableSlots.stream()
                .filter(slotStartCompatible.and(slotLongEnough))
                .findFirst();
    }

}
