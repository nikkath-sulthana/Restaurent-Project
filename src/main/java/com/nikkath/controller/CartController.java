package com.nikkath.controller;

import com.nikkath.model.Cart;
import com.nikkath.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Create new cart
    @PostMapping
    public Cart createCart() {

        return cartService.createCart();
    }
}
