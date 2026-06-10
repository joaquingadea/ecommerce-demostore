package com.api.ecommerce.products.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

public record CreateProductDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        List<MultipartFile> images,
        List<Long> categories
) {
}
