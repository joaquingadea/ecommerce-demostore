package com.api.ecommerce.orders.dto.response;

import java.math.BigDecimal;

public interface OrderDetailDTO {

    String getProductName();
    Integer getQuantity();
    BigDecimal getUnitPrice();
    BigDecimal getSubtotal();

}
