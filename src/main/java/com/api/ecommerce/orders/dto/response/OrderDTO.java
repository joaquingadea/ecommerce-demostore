package com.api.ecommerce.orders.dto.response;

import com.api.ecommerce.orders.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDTO{

    interface UserProjection{
        String getUsername();
    }

    Long getId();
    BigDecimal getTotal();
    OrderStatus getStatus();
    UserProjection getUser();
    LocalDateTime getCreatedAt();
}
