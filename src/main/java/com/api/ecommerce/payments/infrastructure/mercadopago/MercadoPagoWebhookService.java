package com.api.ecommerce.payments.infrastructure.mercadopago;

import com.api.ecommerce.orders.application.IOrderService;
import com.api.ecommerce.payments.domain.PaymentGateway;
import com.api.ecommerce.payments.domain.PaymentStatus;
import com.api.ecommerce.payments.dto.response.ExternalPaymentDetailsDTO;
import com.api.ecommerce.payments.infrastructure.mercadopago.dto.request.MercadoPagoNotificationDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MercadoPagoWebhookService {

    private final PaymentGateway paymentGateway;
    private final IOrderService orderService;

    public MercadoPagoWebhookService(PaymentGateway paymentGateway, IOrderService orderService) {
        this.paymentGateway = paymentGateway;
        this.orderService = orderService;
    }

    public void process(MercadoPagoNotificationDTO notification) {

        if ("payment".equals(notification.getType())) {

            String paymentId = notification.getData().getId();

            ExternalPaymentDetailsDTO payment = paymentGateway.getPayment(paymentId);

            if (payment.status().equals(PaymentStatus.APPROVED)) {
                //orderService.markAsPaid(payment);
            }
        }
    }
}
