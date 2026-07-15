package com.nikkath.repository;

import com.nikkath.model.Cart;
import com.nikkath.model.DiningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByDiningSession(DiningSession diningSession);


}