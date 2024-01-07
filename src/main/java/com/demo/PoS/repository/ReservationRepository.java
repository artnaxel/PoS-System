package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    @Query("""
            select r from Reservation r
            join fetch r.serviceSlot
            where r.id = :id
            """)
    Optional<Reservation> findByIdWithSlot(UUID id);
}
