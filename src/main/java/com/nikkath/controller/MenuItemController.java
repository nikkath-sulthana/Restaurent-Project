package com.nikkath.controller;


import com.nikkath.model.Category;
import com.nikkath.model.FoodType;
import com.nikkath.model.MenuItem;
import com.nikkath.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    //getting by foodType
    @GetMapping("/foodType/{foodType}")
public List<MenuItem> getMenuByFoodType(@PathVariable FoodType foodType){
        return menuItemRepository.findByFoodType(foodType);
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getMenuByCategory(@PathVariable Category category) {
        return menuItemRepository.findByCategory(category);
    }

    // Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems(){
        return menuItemRepository.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuById(@PathVariable Long id) {
        return menuItemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add a new menu item
 @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem){
        return menuItemRepository.save(menuItem);
    }

// Update menu item (NO lambda)
@PutMapping("/{id}")
public MenuItem updateMenuItem(@PathVariable Long id,
                               @RequestBody MenuItem menuItemDetails) {

    MenuItem menuItem = menuItemRepository.findById(id).orElse(null);

    if (menuItem == null) {
        throw new RuntimeException("MenuItem not found with id " + id);
    }

    menuItem.setName(menuItemDetails.getName());
    menuItem.setPrice(menuItemDetails.getPrice());
    menuItem.setCategory(menuItemDetails.getCategory());
    menuItem.setAvailable(menuItemDetails.isAvailable());
    menuItem.setImageUrl(menuItemDetails.getImageUrl());
    menuItem.setFoodType(menuItemDetails.getFoodType());
    return menuItemRepository.save(menuItem);
}


// Delete menu item (NO lambda)
@DeleteMapping("/{id}")
public String deleteMenuItem(@PathVariable Long id) {

    MenuItem menuItem = menuItemRepository.findById(id).orElse(null);

    if (menuItem == null) {
        throw new RuntimeException("MenuItem not found with id " + id);
    }

    menuItemRepository.delete(menuItem);
    return "MenuItem deleted with id: " + id;
}
}