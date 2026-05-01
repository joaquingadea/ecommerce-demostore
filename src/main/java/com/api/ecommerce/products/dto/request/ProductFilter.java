package com.api.ecommerce.products.dto.request;

public record ProductFilter(
        String search,
        Long category
) {
}
