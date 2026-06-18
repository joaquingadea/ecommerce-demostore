package com.api.ecommerce.payments.dto.response;

import com.api.ecommerce.payments.domain.PaymentStatus;

import java.math.BigDecimal;

public record PaymentDetailsDTO(
        String externalPaymentId,
        PaymentStatus status,
        BigDecimal amount
) {
}
