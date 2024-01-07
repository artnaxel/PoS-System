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
            join fetch o.orderProducts op
            join fetch op.product
            where o.id = :orderId""")
    Optional<Order> findOrderWithProductsById(UUID orderId);

}
