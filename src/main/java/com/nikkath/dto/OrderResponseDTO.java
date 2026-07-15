package com.nikkath.dto;

import com.nikkath.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Long orderId;

    private Long diningSessionId;

    private LocalDateTime orderTime;

    private OrderStatus status;

    private List<OrderItemResponseDTO> items;

    private double totalAmount;
}