package com.api.ecommerce.products.infrastructure.persistence.specification;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.dto.request.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecificationBuilder {
    public static Specification<Product> build(ProductFilter filter){
        Specification<Product> spec = ((root, query, cb) -> cb.conjunction());

        if(filter == null){
            return spec;
        }
        if(filter.minPrice() != null){
            spec = spec.and(ProductSpecifications.minPrice(filter.minPrice()));
        }

        return spec;
    }
}
