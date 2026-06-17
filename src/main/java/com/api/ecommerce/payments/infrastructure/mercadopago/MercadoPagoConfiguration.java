package com.api.ecommerce.payments.infrastructure;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @PostConstruct // Se ejecuta despues de la iny de dependencias
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}
