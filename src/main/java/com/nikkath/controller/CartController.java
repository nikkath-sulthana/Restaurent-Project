package com.nikkath.controller;

import com.nikkath.dto.ApiResponse;
import com.nikkath.dto.CartItemResponseDTO;
import com.nikkath.dto.CartResponseDTO;
import com.nikkath.model.Cart;
import com.nikkath.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Create Cart
    @PostMapping("/{diningSessionId}")
    public ResponseEntity<CartResponseDTO> createCart(@PathVariable Long diningSessionId) {

        CartResponseDTO response = cartService.createCart(diningSessionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get Cart
    @GetMapping("/{diningSessionId}")
    public ResponseEntity<CartResponseDTO> getCart(
            @PathVariable Long diningSessionId) {

        return ResponseEntity.ok(cartService.getCart(diningSessionId));
    }

    // Add Item to Cart
    @PostMapping("/{diningSessionId}/items/{menuItemId}")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(
            @PathVariable Long diningSessionId,
            @PathVariable Long menuItemId,
            @RequestParam int quantity) {

        CartItemResponseDTO response =
                cartService.addItemToCart(diningSessionId, menuItemId, quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Item Quantity
    @PutMapping("/{diningSessionId}/items/{menuItemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long diningSessionId,
            @PathVariable Long menuItemId,
            @RequestParam int quantity) {

        ApiResponse response =
                cartService.updateItemQuantity(diningSessionId, menuItemId, quantity);

        return ResponseEntity.ok(response);
    }

    // Remove Item from Cart
    @DeleteMapping("/{diningSessionId}/items/{menuItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long diningSessionId,
            @PathVariable Long menuItemId) {

        cartService.removeItemFromCart(diningSessionId, menuItemId);

        return ResponseEntity.ok(
                new ApiResponse("Item removed from cart successfully."));
    }

}