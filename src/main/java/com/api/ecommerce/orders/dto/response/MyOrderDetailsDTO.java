package com.api.ecommerce.orders.dto.response;

import java.math.BigDecimal;

public interface MyOrderDetailsDTO {
    String getProductName();
    Integer getQuantity();
    BigDecimal getUnitPrice();
    BigDecimal getSubtotal();
}
