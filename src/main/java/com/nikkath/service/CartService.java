package com.nikkath.service;

import com.nikkath.model.Cart;
import com.nikkath.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class CartService {

        @Autowired
        private CartRepository cartRepository;

        // Create new cart
        public Cart createCart() {

            Cart cart = new Cart();

            cart.setTotalAmount(0);

            return cartRepository.save(cart);
        }
    }

