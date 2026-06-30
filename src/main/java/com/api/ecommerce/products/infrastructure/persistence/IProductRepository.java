package com.api.ecommerce.products.infrastructure.persistence;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.dto.response.AllDataProductDTO;
import com.api.ecommerce.products.dto.response.LatestProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Optional<AllDataProductDTO> findAllDataProjectedById(Long id);
    Optional<Product> findByName(String newProduct);

    @Query("""
    SELECT
        p.id AS id,
        p.name AS name,
        p.unitPrice AS price,
        (
            SELECT pi.url
            FROM ProductImage pi
            WHERE pi.product = p
              AND pi.id = (
                  SELECT MIN(pi2.id)
                  FROM ProductImage pi2
                  WHERE pi2.product = p
              )
        ) AS imageUrl
    FROM Product p
    ORDER BY p.uploadDate DESC
    """)
    List<LatestProductDTO> findLatestProducts(Pageable pageable);
}
