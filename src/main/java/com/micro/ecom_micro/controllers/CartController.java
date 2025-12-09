package com.micro.ecom_micro.controllers;

import com.micro.ecom_micro.dto.CartItemRequest;
import com.micro.ecom_micro.models.CartItem;
import com.micro.ecom_micro.repository.CartItemRepository;
import com.micro.ecom_micro.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCartItem(
            @RequestHeader("X-User_Id") String userId,
            @RequestBody CartItemRequest request) {

        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest()
                    .body("Product out of Stock or Product not found or User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCartItem(
            @RequestHeader("X-User_Id") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(
            @RequestHeader("X-User_Id") String userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));

    }


}
