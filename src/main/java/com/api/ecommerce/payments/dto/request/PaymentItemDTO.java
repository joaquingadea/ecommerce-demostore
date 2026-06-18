package com.api.ecommerce.payments.dto.request;

import java.math.BigDecimal;

public record PaymentItemDTO(
        String name,
        Integer quantity,
        BigDecimal unitPrice
) {
}
