package com.nikkath.repository;

import com.nikkath.model.CartItem;
import com.nikkath.model.MenuItem;
import com.nikkath.model.Cart;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndMenuItem(Cart cart, MenuItem menuItem);

}