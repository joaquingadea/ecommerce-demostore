package com.api.ecommerce.payments.application;

import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.payments.domain.Payment;

public interface IPaymentService {
    Payment createAttemptPayment(Order order);
}
