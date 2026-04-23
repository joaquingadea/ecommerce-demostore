package com.api.ecommerce.products.api;

import com.api.ecommerce.products.application.IProductService;
import com.api.ecommerce.products.dto.request.CreateProductRequestDTO;
import com.api.ecommerce.products.dto.response.ProductSinglePageDTO;
import com.api.ecommerce.shared.web.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
