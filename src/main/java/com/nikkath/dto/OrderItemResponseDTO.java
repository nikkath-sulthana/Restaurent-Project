package com.nikkath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {

    private Long menuItemId;

    private String menuItemName;

    private int quantity;

    private double unitPrice;

    private double totalPrice;
}
