package com.nikkath.service;

import com.nikkath.dto.MenuItemRequestDTO;
import com.nikkath.dto.MenuItemRespondDTO;
import com.nikkath.exception.ResourceNotFoundException;
import com.nikkath.model.Category;
import com.nikkath.model.FoodType;
import com.nikkath.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikkath.repository.MenuItemRepository;

import java.util.List;
import java.util.Optional;


@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItemRespondDTO> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    //findById
    public MenuItemRespondDTO getMenuById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("MenuItem not found with id : " + id));
        return mapToResponseDTO(menuItem);
    }

    public MenuItemRespondDTO updateMenuItem(Long id, MenuItemRequestDTO dto) {

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("MenuItem not found with this id : " + id));

        menuItem.setName(dto.getName());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setAvailable(dto.getAvailable());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setFoodType(dto.getFoodType());

        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
        return mapToResponseDTO(updatedMenuItem);
    }

    public MenuItemRespondDTO save(MenuItemRequestDTO dto) {
        MenuItem menuItem = mapToEntity(dto);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return mapToResponseDTO(savedMenuItem);
    }

    public List<MenuItemRespondDTO> findByFoodType(FoodType foodType) {

        List<MenuItem> menuItems = menuItemRepository.findByFoodType(foodType);

        return menuItems.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<MenuItemRespondDTO> findByCategory(Category category) {

        List<MenuItem> menuItems = menuItemRepository.findByCategory(category);

        return menuItems.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }


    public void deleteMenuItem(Long id) {

        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("MenuItem not found with id: " + id);
        }

        menuItemRepository.deleteById(id);
    }
    private MenuItem mapToEntity(MenuItemRequestDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setAvailable(dto.getAvailable());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setFoodType(dto.getFoodType());

        return menuItem;
    }

    private MenuItemRespondDTO mapToResponseDTO(MenuItem menuItem) {

        MenuItemRespondDTO dto = new MenuItemRespondDTO();

        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        dto.setCategory(menuItem.getCategory());
        dto.setAvailable(menuItem.isAvailable());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setFoodType(menuItem.getFoodType());

        return dto;
    }
}
