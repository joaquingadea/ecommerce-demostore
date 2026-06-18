package com.api.ecommerce.payments.infrastructure.mercadopago;

import com.api.ecommerce.payments.domain.PaymentGateway;
import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.dto.response.PaymentCreationResultDTO;
import com.api.ecommerce.payments.dto.response.PaymentDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoPaymentGateway implements PaymentGateway {
    @Override
    public PaymentDetailsDTO getPayment(String externalPaymentId) {
        return null;
    }

    @Override
    public PaymentCreationResultDTO createPayment(CreatePaymentDTO request) {
        return null;
    }
}
