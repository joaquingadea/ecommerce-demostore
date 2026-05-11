package com.api.ecommerce.users.dto.request;

import jakarta.validation.constraints.Min;

public record LoginRequestDTO(
        @Min(8)
        String username,
        @Min(8)
        String password
) {
}
