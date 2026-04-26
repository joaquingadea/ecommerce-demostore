package com.api.ecommerce.products.application;

import com.api.ecommerce.products.dto.request.CreateProductDTO;
import com.api.ecommerce.products.dto.request.ProductFilter;
import com.api.ecommerce.products.dto.response.ProductSinglePageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    void create(CreateProductDTO requestDTO);
    ProductSinglePageDTO getSinglePageProductById(Long id);
    Page<ProductSinglePageDTO> getProducts(ProductFilter filter, Pageable pageRequest);
}
