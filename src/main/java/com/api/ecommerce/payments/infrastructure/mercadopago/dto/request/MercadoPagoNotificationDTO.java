package com.api.ecommerce.payments.infrastructure.mercadopago.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MercadoPagoNotificationDTO{

    private String type;
    private Data data;

    @Getter
    @AllArgsConstructor
    public static class Data{
        private String id;
    }
}
