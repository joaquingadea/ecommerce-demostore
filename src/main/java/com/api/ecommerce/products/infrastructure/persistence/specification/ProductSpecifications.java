package com.api.ecommerce.products.infrastructure.persistence.specification;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.domain.ProductStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> isActive(){
        return ((root, query, cb) ->
                cb.equal(root.get("status"), ProductStatus.ACTIVE));
    }
    public static Specification<Product> isInactive(){
        return (root, query, cb) ->
                cb.equal(root.get("status"),ProductStatus.INACTIVE);
    }
    public static Specification<Product> hasStock(){
        return ((root, query, cb) ->
                cb.greaterThan(root.get("stock"),BigDecimal.ZERO));
    }
    public static Specification<Product> withoutStock() {
        return ((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("stock"),BigDecimal.ZERO));
    }
    public static Specification<Product> nameContains(String keywords){
        return ((root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name"))
                        ,"%" + keywords.toLowerCase() + "%"));
    }
    public static Specification<Product> hasCategories(List<Long> categoryIds) {
        return (root, query, cb) -> {
            Join<Product, ProductCategory> categoryJoin =
                    root.join("productCategories");

            query.distinct(true);

            return categoryJoin.get("id").in(categoryIds);
        };
    }
    public static Specification<Product> hasCategory(Long category) {
        return (root, query, cb) -> {
            Join<Product, ProductCategory> categoryJoin =
                    root.join("productCategories");

            query.distinct(true);

            return cb.equal(
                    categoryJoin.get("id"),
                    category
            );
        };
    }


}
