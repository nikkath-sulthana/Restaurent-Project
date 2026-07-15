package com.nikkath.controller;

import com.nikkath.dto.ApiResponse;
import com.nikkath.dto.OrderResponseDTO;
import com.nikkath.model.OrderStatus;
import com.nikkath.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{diningSessionId}/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@PathVariable("diningSessionId") Long dinningSessionId){

        OrderResponseDTO response = orderService.placeOrder(dinningSessionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderBYId(@PathVariable Long orderId){
         return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status));
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }


    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
