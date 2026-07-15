package com.nikkath.dto;

import com.nikkath.model.Category;
import com.nikkath.model.FoodType;

import lombok.Data;

@Data
public class MenuItemRespondDTO {

    private Long id;
    private String name;
    private double price;
    private Category category;
    private boolean available;
    private String imageUrl;
    private FoodType foodType;
}
