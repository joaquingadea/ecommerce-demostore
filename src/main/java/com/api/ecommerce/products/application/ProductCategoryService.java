package com.api.ecommerce.products.application;

import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.infrastructure.persistence.IProductCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService implements IProductCategoryService{

    private IProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(IProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void create(String requestCategory) {
        ProductCategory newCategory = new ProductCategory(null, requestCategory);
        productCategoryRepository.save(newCategory);
    }

    @Override
    public void edit(Long id, String newName) {
        ProductCategory productCategoryRepo = productCategoryRepository.findById(id).orElseThrow();
        productCategoryRepo.setName(newName);
        productCategoryRepository.save(productCategoryRepo);
    }

    @Override
    public Page<ProductCategory> getAll(Pageable pageRequest) {
        return productCategoryRepository.findAll(pageRequest);
    }

    @Override
    public List<ProductCategory> getCategoryList() {
        return productCategoryRepository.findAll();
    }
}
