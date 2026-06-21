package com.api.ecommerce.payments.infrastructure.mercadopago;

import com.api.ecommerce.payments.domain.PaymentGateway;
import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.dto.response.PaymentCreationResultDTO;
import com.api.ecommerce.payments.dto.response.PaymentDetailsDTO;
import com.api.ecommerce.payments.infrastructure.mercadopago.dto.MercadoPagoMapper;
import com.api.ecommerce.payments.infrastructure.mercadopago.dto.request.CreatePreferenceDTO;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.paymentmethod.PaymentMethodClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MercadoPagoPaymentGateway implements PaymentGateway {

    @Value("${app.url}")
    private String urlBackend;

    private final MercadoPagoMapper mercadoPagoMapper;

    public MercadoPagoPaymentGateway(MercadoPagoMapper mercadoPagoMapper) {
        this.mercadoPagoMapper = mercadoPagoMapper;
    }

    @Override
    public PaymentDetailsDTO getPayment(String externalPaymentId) {
        return null;
    }

    @Override
    public PaymentCreationResultDTO createPayment(CreatePaymentDTO request){

        CreatePreferenceDTO dto = mercadoPagoMapper.toPreference(request);

        Preference preference = createPreference(dto);

        return new PaymentCreationResultDTO(
                preference.getId(), // setteo de preference id
                preference.getInitPoint() // setteo de url checkout
        );

    }

    public Preference createPreference(CreatePreferenceDTO dto){

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(dto.items())
                .externalReference(dto.externalReference())
                .backUrls(dto.backUrls())
                .build();

        PreferenceClient client = new PreferenceClient();

        try {
            return client.create(preferenceRequest);
        }
        catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }
    }
}
