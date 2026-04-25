package com.api.ecommerce.products.infrastructure.persistence.specification;

import com.api.ecommerce.products.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> minPrice(BigDecimal min){
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("unitPrice"),min);
    }

}
