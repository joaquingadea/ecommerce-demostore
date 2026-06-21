package com.api.ecommerce.payments.infrastructure.mercadopago.dto;

import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.infrastructure.mercadopago.dto.request.CreatePreferenceDTO;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoPagoMapper {

    @Value("${app.url}")
    private String urlBackend;

    public CreatePreferenceDTO toPreference(CreatePaymentDTO dto){

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(dto.successUrl())
                .pending(dto.pendingUrl())
                .failure(dto.failureUrl())
                .build();

        List<PreferenceItemRequest> items = dto.items().stream()
                .map(p -> PreferenceItemRequest.builder()
                        .id(p.orderDetailId())
                        .title(p.name())
                        .description(p.description())
                        .pictureUrl(urlBackend.concat(p.firstImageUrl()))
                        .quantity(p.quantity())
                        .unitPrice(p.unitPrice())
                        .build()
                )
                .toList();

        return new CreatePreferenceDTO(
                dto.orderId().toString(),
                items,
                backUrls
        );
    }
}
