package com.api.ecommerce.products.dto.response;

import com.api.ecommerce.products.domain.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AllDataProductDTO {
        interface idAndName{
            Long getId();
            String getName();
        }
        interface idAndUrl{
            Long getId();
            String getUrl();
        }

        Long getId();
        String getName();
        String getDescription();
        BigDecimal getUnitPrice();
        Integer getStock();
        Integer getUnitsSold();
        List<idAndName> getProductCategories();
        List<idAndUrl> getImages();
        String getStatus();
}
