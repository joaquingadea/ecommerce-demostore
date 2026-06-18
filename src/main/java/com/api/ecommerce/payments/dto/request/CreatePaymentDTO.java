package com.api.ecommerce.payments.dto.request;

import com.mercadopago.resources.payment.PaymentItem;

import java.util.List;

public record CreatePaymentDTO(
        Long paymentId,
        List<PaymentItem> items,
        String successUrl,
        String pendingUrl,
        String failureUrl
) {
}
