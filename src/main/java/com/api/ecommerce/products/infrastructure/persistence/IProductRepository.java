package com.api.ecommerce.products.infrastructure.persistence;

import com.api.ecommerce.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {}
