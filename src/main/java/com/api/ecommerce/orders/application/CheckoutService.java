package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.infrastructure.ICartRepository;
import com.api.ecommerce.payments.domain.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private PaymentGateway paymentGateway;
    private ICartRepository cartRepository;

    public CheckoutService(PaymentGateway paymentGateway, ICartRepository cartRepository) {
        this.paymentGateway = paymentGateway;
        this.cartRepository = cartRepository;
    }

    public void checkout(Long userId){
        // obtener y validar carrito
        // validar stock y precios
        // se crea la orden para BD
        // se crea el intento de pago
        // preference de MP
        // devolver URL del checkout
    }
}
