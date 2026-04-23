package com.api.ecommerce.products.application;

import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.infrastructure.persistence.IProductCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements IProductCategoryService{

    private IProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(IProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void create(String requestCategory) {
        ProductCategory newCategory = new ProductCategory(1L, requestCategory);
        productCategoryRepository.save(newCategory);
    }
}
