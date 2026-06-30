package com.api.ecommerce.products.infrastructure.persistence;

import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.dto.response.PublicCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    @Query(" SELECT c.id AS id, c.name AS name FROM ProductCategory c ")
    List<PublicCategoryDTO> findAllPublicCategories();
}
