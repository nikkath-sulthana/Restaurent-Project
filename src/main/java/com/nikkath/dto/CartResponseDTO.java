package com.nikkath.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {
    private Long cartId;

    private Long diningSessionId;

    private List<CartItemResponseDTO> items;

    private double totalAmount;
}
