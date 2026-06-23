package com.api.ecommerce.orders.infrastructure.persistence;

import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.orders.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
}
