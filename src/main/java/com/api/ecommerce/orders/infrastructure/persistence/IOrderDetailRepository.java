package com.api.ecommerce.orders.infrastructure.persistence;

import com.api.ecommerce.orders.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
