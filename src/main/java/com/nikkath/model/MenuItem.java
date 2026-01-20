package com.nikkath.model;


import jakarta.persistence.*;


@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @Enumerated(EnumType.STRING)
     private Category category;

     private boolean available = true;

     public String checkMenuAvailability(){
        return available? "Yes" : "NO";
     }
 private String imageUrl;

     @Enumerated(EnumType.STRING)
     private FoodType foodType;

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public MenuItem(){};

    public MenuItem(String name, double price, Category category, boolean available, String imageUrl, FoodType foodType) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.imageUrl = imageUrl;
        this.foodType = foodType;
    }
}
