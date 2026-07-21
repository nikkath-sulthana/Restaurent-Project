package com.nikkath.repository;

import com.nikkath.model.DiningSession;
import com.nikkath.model.Order;
import com.nikkath.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{

   List<Order> findByDiningSession(DiningSession diningSession);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByDiningSessionAndStatus(DiningSession diningSession, OrderStatus status);
}
