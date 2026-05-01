package com.api.ecommerce.products.infrastructure.persistence.specification;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.dto.request.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecificationBuilder {
    public static Specification<Product> build(ProductFilter filter){
        Specification<Product> spec = Specification.where(ProductSpecifications.isActive());

        if(filter == null){
            return spec;
        }
        if(filter.search() != null && !filter.search().isBlank()){
            spec = spec.and(ProductSpecifications.nameContains(filter.search()));
        }
        if(filter.category() != null){
            spec = spec.and(ProductSpecifications.hasCategory(filter.category()));
        }
        return spec;
    }
}
