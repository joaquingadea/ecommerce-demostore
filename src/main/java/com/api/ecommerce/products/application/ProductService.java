package com.api.ecommerce.products.application;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.domain.ProductImage;
import com.api.ecommerce.products.dto.request.CreateProductRequestDTO;
import com.api.ecommerce.products.dto.response.ProductSinglePageDTO;
import com.api.ecommerce.products.infrastructure.persistence.IProductCategoryRepository;
import com.api.ecommerce.products.infrastructure.persistence.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{

    @Value("app.url")
    private String urlApp;

    private IProductRepository productRepository;
    private FileStorageService fileStorageService;
    private IProductCategoryRepository categoryRepository;

    public ProductService(IProductRepository productRepository, FileStorageService fileStorageService, IProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
        this.categoryRepository = categoryRepository;
    }

    public ProductImage mapToImage(MultipartFile file, Product newProduct){
        return new ProductImage(1L, fileStorageService.saveFile(file),newProduct);
    }

    @Override
    public void create(CreateProductRequestDTO requestDTO) {
        Product newProduct = new Product(
                1L,
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.stock(),
                requestDTO.price(),
                null,
                null);

        Set<ProductImage> images = requestDTO.images()
                .stream().map(file -> mapToImage(file,newProduct))
                .collect(Collectors.toSet());

        Set<ProductCategory> categories =
                new HashSet<>(categoryRepository.findAllById(requestDTO.categories()));

        newProduct.setImages(images);
        newProduct.setProductCategories(categories);

        productRepository.save(newProduct);
    }

    @Override
    public ProductSinglePageDTO getSinglePageProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductSinglePageDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getUnitPrice(),
                        product.getStock(),
                        product.getProductCategories().stream()
                                .map(ProductCategory::getName).toList(),
                        product.getImages().stream()
                                .map(image -> urlApp + image.getUrl()).toList()
                ))
                .orElseThrow(EntityNotFoundException::new);
    }


}
