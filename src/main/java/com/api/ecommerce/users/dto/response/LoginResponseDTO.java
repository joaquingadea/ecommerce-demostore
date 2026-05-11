package com.api.ecommerce.users.dto.response;

import java.util.List;

public record LoginResponseDTO(
        String message,
        List<String> authorities
) {
}
