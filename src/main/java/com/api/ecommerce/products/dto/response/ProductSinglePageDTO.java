package com.api.ecommerce.products.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductSinglePageDTO(
        Long id,
        String name,
        String description,
        BigDecimal unitPrice,
        Integer stock,
        List<?> categories,
        List<?> images
) {
}
