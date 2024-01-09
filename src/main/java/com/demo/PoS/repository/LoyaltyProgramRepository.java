package com.demo.PoS.repository;

import com.demo.PoS.model.entity.LoyaltyProgram;
import com.demo.PoS.model.relationship.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, UUID> {
    @Query("""
            select new org.springframework.data.util.Pair(op, cl) from OrderProduct op
            join fetch op.order o
            join fetch o.customer c
            join fetch c.loyaltyProgram cl
            join fetch op.product p
            join fetch p.loyaltyProgram pl
            where o.id = :orderId
            and cl.id = pl.id
            """)
    Optional<Set<Pair<OrderProduct, LoyaltyProgram>>> findLoyaltyProgramsWithOrderProductByOrder(UUID orderId);
}
