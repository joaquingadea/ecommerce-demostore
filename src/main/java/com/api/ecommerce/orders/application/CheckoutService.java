package com.api.ecommerce.orders.application;

import com.api.ecommerce.cart.application.ICartService;
import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.cart.infrastructure.ICartRepository;
import com.api.ecommerce.orders.domain.Order;
import com.api.ecommerce.orders.dto.response.CheckoutResponseDTO;
import com.api.ecommerce.payments.application.IPaymentService;
import com.api.ecommerce.payments.domain.Payment;
import com.api.ecommerce.payments.domain.PaymentGateway;
import com.api.ecommerce.payments.dto.request.CreatePaymentDTO;
import com.api.ecommerce.payments.dto.request.PaymentItemDTO;
import com.api.ecommerce.payments.dto.response.PaymentCreationResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckoutService {

    @Value("${app.url.frontend.ecommerce}")
    private String urlFrontEcommerce;

    private final PaymentGateway paymentGateway;
    private final IPaymentService paymentService;
    private final ICartRepository cartRepository;
    private final IOrderService orderService;
    private final ICartService cartService;

    public CheckoutService(PaymentGateway paymentGateway, IPaymentService paymentService, ICartRepository cartRepository, IOrderService orderService, ICartService cartService) {
        this.paymentGateway = paymentGateway;
        this.paymentService = paymentService;
        this.cartRepository = cartRepository;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    public CheckoutResponseDTO checkout(Long userId){
        // obtener y validar carrito
        Cart cart = cartRepository.findByUserId(userId).orElseThrow();
        cartService.validateCart(cart);
        // se crea la orden para BD
        Order order = orderService.getOrCreateOrder(userId,cart);
        // se crea el intento de pago o se obtiene el pendiente (se tira una excepcion si esta aprobado)
        Payment payment = paymentService.getOrCreateAttemptPayment(order);
        // payment de MP
        CreatePaymentDTO requestDTO = new CreatePaymentDTO(
                order.getId(),
                order.getOrderDetails().stream().map(orderDetail -> new PaymentItemDTO(
                        orderDetail.getId().toString(),
                        orderDetail.getProduct().getName(),
                        orderDetail.getProduct().getDescription(),
                        orderDetail.getQuantity(),
                        orderDetail.getProduct().getImages().get(0).getUrl(),
                        orderDetail.getUnitPrice()
                )).toList(),
                urlFrontEcommerce + "/mp/success",
                urlFrontEcommerce + "/mp/pending",
                urlFrontEcommerce + "/mp/failure"
        );

        PaymentCreationResultDTO paymentResultDTO = paymentGateway.createPayment(requestDTO);

        payment.setMercadoPagoPreferenceId(paymentResultDTO.preferenceId());

        return new CheckoutResponseDTO(paymentResultDTO.checkoutUrl());
    }

}
