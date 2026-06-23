package com.api.ecommerce.payments.infrastructure.mercadopago.dto.request;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;

import java.util.List;

public record CreatePreferenceDTO(
        String externalReference, // externo para la API de mercado pago (dentro del sistema es paymentId)
        List<PreferenceItemRequest> items,
        PreferenceBackUrlsRequest backUrls
) {
}
