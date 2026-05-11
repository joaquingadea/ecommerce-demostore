package com.api.ecommerce.users.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequestDTO(
        @NotEmpty
        String name,
        @NotEmpty
        String lastName,
        @Min(8)
        String username,
        @Min(8)
        String password,
        @Min(8)
        String confirmPassword
) {
}
