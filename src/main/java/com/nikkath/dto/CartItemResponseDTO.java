package com.nikkath.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long menuItemId;

    private String menuItemName;

    private int quantity;

    private double unitPrice;

    private double totalPrice;
}
