package com.nikkath.dto;

import com.nikkath.model.Category;
import com.nikkath.model.FoodType;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

import lombok.Data;


@Data
public class MenuItemRequestDTO {

    @NotBlank(message = "Menu name is required")
    private String name;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @NotNull(message = "Category is required")
    private Category category;

    //Boolean (Wrapper class)
    //It can have:true/false/null
    //Now @NotNull works.
    //If the frontend forgets to send available, Spring will return a validation error.
    //For request DTOs, Boolean is usually preferred.
    @NotNull(message = "Availability is required")
    private Boolean available;

    @NotBlank(message = "Image URL is required")
    @Pattern(
            regexp = "^(https?|ftp)://.*$",
            message = "Please enter a valid URL"
    )
    private String imageUrl;


    @NotNull(message = "Food type is required")
    private FoodType foodType;
}