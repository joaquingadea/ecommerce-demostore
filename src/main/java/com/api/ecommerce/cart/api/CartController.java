package com.api.ecommerce.cart.api;

import com.api.ecommerce.cart.application.ICartService;
import com.api.ecommerce.cart.dto.response.CartItemDTO;
import com.api.ecommerce.shared.web.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/item/add/{productId}")
    public ResponseEntity<ApiResponse> addItem(@PathVariable Long productId, @RequestBody Integer quantity, Authentication authentication){
        cartService.addItem(productId,quantity,null); // sacado de la authentication
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product added successfully!"));
    }
    @PatchMapping("/item/increase/{itemId}")
    public ResponseEntity<ApiResponse> increaseItemQuantity(@PathVariable Long itemId, Authentication authentication){
        cartService.increaseItem(itemId,null); // sacado de la authentication
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item updated successfully!"));
    }
    @PatchMapping("/item/decrease/{itemId}")
    public ResponseEntity<ApiResponse> decreaseItemQuantity(@PathVariable Long itemId, Authentication authentication){
        cartService.decreaseItem(itemId,null); // sacado de la authentication
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item updated successfully!"));
    }
    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId,Authentication authentication){
        cartService.deleteItem(itemId,null);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item deleted successfully!"));
    }
    @GetMapping("/get")
    public ResponseEntity<List<CartItemDTO>> getCart(Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.getCart(null)); // sacado de la authentication
    }
    @GetMapping("/get-total-cart")
    public ResponseEntity<BigDecimal> getTotalCart(Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.getTotal(null)); // sacado de la authentication
    }
}
