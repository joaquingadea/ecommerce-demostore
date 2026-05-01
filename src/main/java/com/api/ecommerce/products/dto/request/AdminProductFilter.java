package com.api.ecommerce.products.dto.request;

import java.util.List;

public record AdminProductFilter(
        String search,
        List<Long> categoryIds,
        Boolean active,
        Boolean bestSellers,
        Boolean hasStock
) {
}
