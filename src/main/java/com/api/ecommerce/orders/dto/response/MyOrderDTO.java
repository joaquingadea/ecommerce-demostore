package com.api.ecommerce.orders.dto.response;

import com.api.ecommerce.orders.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MyOrderDTO {
    Long getId();
    LocalDateTime getCreatedAt();
    BigDecimal getTotal();
    OrderStatus getStatus();
}
