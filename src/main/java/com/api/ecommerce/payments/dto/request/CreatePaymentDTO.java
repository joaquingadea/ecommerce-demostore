package com.api.ecommerce.payments.dto.request;


import java.util.List;

public record CreatePreferenceDTO(
        Long orderId,
        List<PaymentItemDTO> items,
        String successUrl,
        String pendingUrl,
        String failureUrl
) {
}
