package com.api.ecommerce.products.api;

import com.api.ecommerce.products.application.IProductService;
import com.api.ecommerce.products.dto.request.ProductFilter;
import com.api.ecommerce.products.dto.response.ProductSinglePageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/get-single-page/{id}")
    public ResponseEntity<ProductSinglePageDTO> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getSinglePageProductById(id));
    }

    @GetMapping("/get-products")
    public ResponseEntity<Page<ProductSinglePageDTO>> getProducts(
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false)BigDecimal minPrice,
            @PageableDefault(size = 9, page = 0) Pageable pageable){
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        ProductFilter filter = new ProductFilter(categoryIds,minPrice);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProducts(filter,pageRequest));
    }
}
