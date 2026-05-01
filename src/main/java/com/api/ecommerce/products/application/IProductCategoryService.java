package com.api.ecommerce.products.application;

public interface IProductCategoryService {
    void create(String requestCategory);
    void edit(Long id, String newName);
}
