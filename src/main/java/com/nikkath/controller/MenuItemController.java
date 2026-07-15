package com.nikkath.controller;


import com.nikkath.dto.MenuItemRequestDTO;
import com.nikkath.dto.MenuItemRespondDTO;

import com.nikkath.model.Category;
import com.nikkath.model.FoodType;

import com.nikkath.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;


    // Get all menu items
    @GetMapping
    public ResponseEntity<List<MenuItemRespondDTO>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }


    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemRespondDTO> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuById(id));
    }


    // Add a new menu item
    @PostMapping
    public ResponseEntity<MenuItemRespondDTO> addMenuItem(@Valid @RequestBody MenuItemRequestDTO dto) {
        return ResponseEntity.ok(menuItemService.save(dto));
    }


    //getting by foodType
    @GetMapping("/foodType/{foodType}")
    public List<MenuItemRespondDTO> getMenuByFoodType(
            @PathVariable FoodType foodType) {

        return menuItemService.findByFoodType(foodType);
    }


    //getting by category
    @GetMapping("/category/{category}")
    public List<MenuItemRespondDTO> getMenuByCategory(@PathVariable Category category) {
        return menuItemService.findByCategory(category);
    }


    // Update menu item (NO lambda)
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemRespondDTO> updateMenuItem(@PathVariable Long id,
                                             @Valid @RequestBody MenuItemRequestDTO dto) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, dto));
    }


    // Delete menu item (NO lambda)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {

        menuItemService.deleteMenuItem(id);

        return ResponseEntity.ok("MenuItem deleted successfully with id: " + id);
    }
}