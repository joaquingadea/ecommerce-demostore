package com.api.ecommerce.products.infrastructure.persistence;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.dto.response.AllDataProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Optional<AllDataProductDTO> findAllDataProjectedById(Long id);
    Optional<Product> findByName(String newProduct);
}
