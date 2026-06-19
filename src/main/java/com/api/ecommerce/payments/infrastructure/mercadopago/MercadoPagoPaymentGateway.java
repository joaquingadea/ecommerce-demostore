package com.api.ecommerce.payments.infrastructure.mercadopago;

import com.api.ecommerce.payments.domain.PaymentGateway;
import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.dto.request.PaymentItemDTO;
import com.api.ecommerce.payments.dto.response.PaymentCreationResultDTO;
import com.api.ecommerce.payments.dto.response.PaymentDetailsDTO;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class MercadoPagoPaymentGateway implements PaymentGateway {

    @Value("${app.url}")
    private String urlBackend;

    @Override
    public PaymentDetailsDTO getPayment(String externalPaymentId) {
        return null;
    }

    @Override
    public PaymentCreationResultDTO createPayment(CreatePaymentDTO request){
        Preference preference = createPreference(request.items());

        return new PaymentCreationResultDTO(
                preference.getId(), // setteo de preference id
                preference.getInitPoint() // setteo de url checkout
        );

    }

    public Preference createPreference(List<PaymentItemDTO> paymentItems){

        List<PreferenceItemRequest> items = paymentItems.stream()
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

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();

        PreferenceClient client = new PreferenceClient();

        try {
            return client.create(preferenceRequest);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }
    }
}
