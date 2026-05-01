package com.api.ecommerce.products.dto.response;

import java.math.BigDecimal;
import java.util.List;

public interface PublicProductDTO{
        Long getId();
        String getName();
        BigDecimal getUnitPrice();
        List<idAndName> getProductCategories();
        List<idAndUrl> getImages();

        interface idAndName{
            Long getId();
            String getName();
        }
        interface idAndUrl{
            Long getId();
            String getUrl();
        }
}
