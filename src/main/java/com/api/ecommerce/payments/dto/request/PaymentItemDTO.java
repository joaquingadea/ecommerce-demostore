package com.api.ecommerce.payments.dto.request;

import java.math.BigDecimal;

public record PaymentItemDTO(
        String orderDetailId,
        String name,
        String description,
        Integer quantity,
        String firstImageUrl,
        BigDecimal unitPrice
) {
}
