package com.api.ecommerce.payments.domain;

import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.dto.response.PaymentCreationResultDTO;
import com.api.ecommerce.payments.dto.response.ExternalPaymentDetailsDTO;

public interface PaymentGateway {
    ExternalPaymentDetailsDTO getPayment(String externalPaymentId);
    PaymentCreationResultDTO createPayment(CreatePaymentDTO request);
}
