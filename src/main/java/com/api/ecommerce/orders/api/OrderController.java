package com.api.ecommerce.orders.api;

import com.api.ecommerce.orders.application.CheckoutService;
import com.api.ecommerce.orders.application.IOrderService;
import com.api.ecommerce.shared.security.jwt.JwtPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/order")
public class OrderController {

    private IOrderService orderService;
    private CheckoutService checkoutService;

    public OrderController(IOrderService orderService, CheckoutService checkoutService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@AuthenticationPrincipal JwtPrincipal auth){
        checkoutService.checkout(auth.userId());
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
