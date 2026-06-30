package com.api.ecommerce.products.application;

import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.dto.response.PublicCategoryDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductCategoryService {
    void create(String requestCategory);
    void edit(Long id, String newName);
    Page<ProductCategory> getAll(Pageable pageRequest);
    List<ProductCategory> getCategoryList();
    List<PublicCategoryDTO> getPublicCategories();
}
