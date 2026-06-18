package com.api.ecommerce.payments.dto.response;

public record PaymentCreationResultDTO(
        String externalReference,
        String checkoutUrl
) {
}
