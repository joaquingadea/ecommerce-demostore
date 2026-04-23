package com.api.ecommerce.products.application;

import com.api.ecommerce.products.dto.request.CreateProductRequestDTO;
import com.api.ecommerce.products.dto.response.ProductSinglePageDTO;
import org.jspecify.annotations.Nullable;

public interface IProductService {
    void create(CreateProductRequestDTO requestDTO);
    ProductSinglePageDTO getSinglePageProductById(Long id);
}
