package com.api.ecommerce.payments.application;

import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.payments.domain.Payment;
import com.api.ecommerce.payments.domain.PaymentStatus;
import com.api.ecommerce.payments.infrastructure.persistence.IPaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService implements IPaymentService {

    private IPaymentRepository paymentRepository;

    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createAttemptPayment(Order order){
        Payment payment = new Payment(
                null,
                null,
                null,
                LocalDateTime.now(),
                order.getTotal(),
                PaymentStatus.PENDING,
                order
        );
        paymentRepository.save(payment);
        return payment;
    }
}
