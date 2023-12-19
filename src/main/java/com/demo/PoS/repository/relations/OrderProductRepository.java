package com.demo.PoS.repository.relations;

import com.demo.PoS.model.relationship.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProduct.OrderProductKey> {
}
