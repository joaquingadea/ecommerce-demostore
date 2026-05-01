package com.api.ecommerce.products.infrastructure.persistence.specification;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.dto.request.AdminProductFilter;
import org.springframework.data.jpa.domain.Specification;

public class AdminProductSpecBuilder {
    public static Specification<Product> build(AdminProductFilter filter) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (filter == null){
            return spec;
        }
        if (filter.search() != null && !filter.search().isBlank()){
            spec = spec.and(ProductSpecifications.nameContains(filter.search()));
        }
        if (filter.categoryIds() != null && !filter.categoryIds().isEmpty()){
            spec = spec.and(ProductSpecifications.hasCategories(filter.categoryIds()));
        }
        if (filter.active() != null){
            if (filter.active().equals(Boolean.TRUE)){
                spec = spec.and(ProductSpecifications.isActive());
            }
            else {
                spec = spec.and(ProductSpecifications.isInactive());
            }
        }
        if (filter.hasStock() != null){
            if (filter.hasStock().equals(Boolean.TRUE)){
                spec = spec.and(ProductSpecifications.hasStock());
            }
            else {
                spec = spec.and(ProductSpecifications.withoutStock());
            }
        }
        return spec;
    }
}
