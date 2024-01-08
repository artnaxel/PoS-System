package com.demo.PoS.repository;

import com.demo.PoS.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
            select o from Order o
            left join fetch o.orderProducts op
            left join fetch op.product
            left join fetch o.reservations r
            left join fetch r.serviceSlot ss
            left join fetch ss.providedService
            where o.id = :orderId
            """)
    Optional<Order> findOrderWithProductsAndServicesById(UUID orderId);

    @Query("""
            select o from Order o
            left join fetch o.orderProducts op
            left join fetch op.product p
            left join fetch p.discount
            left join fetch o.reservations r
            left join fetch r.serviceSlot ss
            left join fetch ss.providedService ps
            left join fetch ps.discount
            where o.id = :orderId
            """)
    Optional<Order> findOrderWithProductsAndServicesAndDiscountsById(UUID orderId);


}
