package com.api.ecommerce.products.application;

import com.api.ecommerce.products.domain.Product;
import com.api.ecommerce.products.domain.ProductCategory;
import com.api.ecommerce.products.domain.ProductImage;
import com.api.ecommerce.products.domain.ProductStatus;
import com.api.ecommerce.products.dto.request.CreateProductDTO;
import com.api.ecommerce.products.dto.request.EditProductDTO;
import com.api.ecommerce.products.infrastructure.persistence.IProductCategoryRepository;
import com.api.ecommerce.products.infrastructure.persistence.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private IProductCategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Nested
    class CreateProductTests{
        @Test
        void shouldCreateProductSuccessfully() {

            MultipartFile image = mock(MultipartFile.class);

            CreateProductDTO dto = new CreateProductDTO(
                    "Mouse",
                    "Gaming Mouse",
                    BigDecimal.valueOf(100),
                    10,
                    List.of(image),
                    List.of(1L)
            );

            ProductCategory category = new ProductCategory();
            category.setId(1L);

            when(categoryRepository.findAllById(List.of(1L)))
                    .thenReturn(List.of(category));

            when(fileStorageService.saveFile(image))
                    .thenReturn("/upload/image.jpg");


            productService.create(dto);

            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

            verify(productRepository).save(productCaptor.capture());

            Product savedProduct = productCaptor.getValue();

            assertEquals("Mouse",savedProduct.getName());
            assertEquals("Gaming Mouse",savedProduct.getDescription());
            assertEquals(BigDecimal.valueOf(100),savedProduct.getUnitPrice());
            assertEquals(10,savedProduct.getStock());
            assertEquals(ProductStatus.ACTIVE,savedProduct.getStatus());
            assertEquals(1,savedProduct.getImages().size());
            assertEquals(1,savedProduct.getProductCategories().size());
        }
    }

    @Nested
    class EditProductTests{
        @Test
        void shouldEditProductBasicFieldsAndCategories(){

            Product product = new Product();
            product.setImages(new ArrayList<>());
            product.setProductCategories(new ArrayList<>());

            when(productRepository.findById(1L))
                    .thenReturn(Optional.of(product));

            when(categoryRepository.findAllById(List.of(1L)))
                    .thenReturn(List.of(new ProductCategory()));

            EditProductDTO dto = new EditProductDTO(
                    "New Name",
                    "New Desc",
                    BigDecimal.TEN,
                    5,
                    List.of(),
                    List.of(),
                    List.of(1L)
            );

            productService.editById(1L, dto);
            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(productRepository).save(productCaptor.capture());

            Product productEdited = productCaptor.getValue();

            assertEquals("New Name", productEdited.getName());
            assertEquals("New Desc", productEdited.getDescription());
            assertEquals(BigDecimal.TEN, productEdited.getUnitPrice());
            assertEquals(5, productEdited.getStock());
            assertEquals(1, productEdited.getProductCategories().size());

        }

        @Test
        void shouldAddOrDeleteImages(){
            Product product = new Product();

            MultipartFile newImage = mock(MultipartFile.class);

            ProductImage i1 = new ProductImage(1L,"/uploads/img1.jpg");
            ProductImage i2 = new ProductImage(2L,"/uploads/img2.jpg");
            ProductImage i3 = new ProductImage(3L,"/uploads/img3.jpg");

            List<ProductImage> images = new ArrayList<>();
            images.add(i1); images.add(i2); images.add(i3);
            product.setProductCategories(new ArrayList<>());
            product.setImages(images);

            when(productRepository.findById(1L))
                    .thenReturn(Optional.of(product));

            ProductCategory category = new ProductCategory(); category.setId(1L);
            when(categoryRepository.findAllById(List.of(1L)))
                    .thenReturn(List.of(category));

            EditProductDTO dto = new EditProductDTO(
                    "New Name",
                    "New Desc",
                    BigDecimal.TEN,
                    5,
                    List.of(1L),
                    List.of(newImage),
                    List.of(1L)
            );


            productService.editById(1L, dto);

            verify(fileStorageService).deleteImage("/uploads/img1.jpg"); // se elimina una foto
            verify(fileStorageService).saveFile(newImage); // se agrega una foto

            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(productRepository).save(productCaptor.capture());
            Product productEdited = productCaptor.getValue();

            assertEquals(3, productEdited.getImages().size());

        }

        @Test
        void shouldThrowEntityNotFoundExceptionIfCategoryNotFound(){
            Product product = new Product();
            product.setImages(new ArrayList<>());
            product.setProductCategories(new ArrayList<>());

            when(productRepository.findById(1L))
                    .thenReturn(Optional.of(product));

            when(categoryRepository.findAllById(List.of()))
                    .thenThrow(new EntityNotFoundException("One or more categories not found!"));

            EditProductDTO dto = new EditProductDTO(
                    "name",
                    "desc",
                    BigDecimal.TEN,
                    10,
                    List.of(),   // categories
                    List.of(),   // deleteImages
                    List.of()    // newImages
            );

            assertThrows(EntityNotFoundException.class, () -> {
                productService.editById(1L, dto);
            });

        }

    }

    @Nested
    class UpdateStatusTests{
        @Test
        void shouldDeactivateProduct(){

            Product product = new Product();

            when(productRepository.findById(1L))
                    .thenReturn(Optional.of(product));

            productService.deactivate(1L);

            verify(productRepository).findById(1L);

            assertEquals(ProductStatus.INACTIVE,product.getStatus());
        }
        @Test
        void shouldActivateProduct(){

            Product product = new Product();

            when(productRepository.findById(1L))
                    .thenReturn(Optional.of(product));

            productService.activate(1L);

            verify(productRepository).findById(1L);

            assertEquals(ProductStatus.ACTIVE,product.getStatus());
        }
    }
}
