package com.api.ecommerce.products.dto.response;

import java.math.BigDecimal;

public interface LatestProductDTO{

    Long getId();

    String getName();

    BigDecimal getPrice();

    String getImageUrl();

}
