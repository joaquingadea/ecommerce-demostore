package com.api.ecommerce.payments.infrastructure.mercadopago;

import com.api.ecommerce.payments.infrastructure.mercadopago.dto.request.MercadoPagoNotificationDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/mercadopago")
public class MercadoPagoWebhookController {

    private final MercadoPagoWebhookService webhookService;

    public MercadoPagoWebhookController(MercadoPagoWebhookService webhookService) {
        this.webhookService = webhookService;
    }
    @GetMapping
    public ResponseEntity<String> test() {
        System.out.println("GET RECIBIDO");
        return ResponseEntity.ok("OK");
    }
    /*@PostMapping
    public ResponseEntity<Void> receiveWebhook(HttpServletRequest request, @RequestBody(required = false) MercadoPagoNotificationDTO notification){
        try {
            //validate(request);
            webhookService.process(notification);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }*/
    @PostMapping // ENDPOINT DE TEST PARA VER SI FUNCIONA LA CONEXION CON LA API
    public ResponseEntity<Void> receiveWebhook(
            @RequestBody(required = false) String body) {

        System.out.println(body);
        return ResponseEntity.ok().build();
    }
}
