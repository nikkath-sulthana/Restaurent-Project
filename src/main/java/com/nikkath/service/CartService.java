package com.nikkath.service;

import com.nikkath.dto.CartItemResponseDTO;
import com.nikkath.dto.CartResponseDTO;
import com.nikkath.dto.ApiResponse;
import com.nikkath.exception.CartAlreadyExistsException;
import com.nikkath.exception.ResourceNotFoundException;
import com.nikkath.exception.BadRequestException;
import com.nikkath.model.Cart;
import com.nikkath.model.CartItem;
import com.nikkath.model.DiningSession;
import com.nikkath.model.MenuItem;
import com.nikkath.repository.CartItemRepository;
import com.nikkath.repository.CartRepository;
import com.nikkath.repository.DiningSessionRepository;
import com.nikkath.repository.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

    @Service
    @Transactional
    public class CartService {
        @Autowired
        private MenuItemRepository menuItemRepository;

        @Autowired
        private DiningSessionRepository diningSessionRepository;

        @Autowired
        private CartRepository cartRepository;

        @Autowired
        private CartItemRepository cartItemRepository;

        // Create new cart
        public CartResponseDTO createCart(Long diningSessionId) {
            DiningSession diningSession = diningSessionRepository.findById(diningSessionId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Dining session not found with id: " + diningSessionId));
            // Check if cart already exists
            Optional<Cart> existingCart = cartRepository.findByDiningSession(diningSession);
            if(existingCart.isPresent()){
                throw new CartAlreadyExistsException("Cart already exists for dining session id: " + diningSessionId);
            }

            Cart cart = new Cart();

            cart.setTotalAmount(0);

            cart.setDiningSession(diningSession);

            cart = cartRepository.save(cart);
            return mapToCartResponse(cart);
        }



        private void updateCartTotal(Cart cart){
            double total =0;
            for(CartItem items: cart.getItems()){
                total += items.getUnitPrice()*items.getQuantity();
            }
            cart.setTotalAmount(total);
            cartRepository.save(cart);
        }


        private Cart getCartByDiningSession(Long diningSessionId){

            DiningSession diningSession = diningSessionRepository.findById(diningSessionId).orElseThrow(()
                    -> new ResourceNotFoundException("Dining Session Not Found"));

            Cart cart = cartRepository.findByDiningSession(diningSession).orElseThrow(()
                    -> new ResourceNotFoundException("Cart Not Found"));

            return cart;

        }

        public CartResponseDTO getCart(Long diningSessionId) {

            Cart cart = getCartByDiningSession(diningSessionId);

            return mapToCartResponse(cart);
        }

        public CartItemResponseDTO addItemToCart(Long diningSessionId, Long menuItemId, int quantity){

            System.out.println("Inside addItemToCart");
            Cart cart = getCartByDiningSession(diningSessionId);

            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                    new ResourceNotFoundException("Menu Item Not found"));
            if(quantity <=0 ){
                throw new IllegalArgumentException("Quantity should be more than  Zero");
            }
            Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndMenuItem(cart, menuItem);
            if(existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                CartItem savedItem = cartItemRepository.save(cartItem);
                updateCartTotal(cart);
                return mapToCartItemResponse(savedItem);
            }
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setMenuItem(menuItem);
                cartItem.setUnitPrice(menuItem.getPrice());
                cartItem.setQuantity(quantity);

           CartItem savedItem =  cartItemRepository.save(cartItem);

           cart.getItems().add(savedItem);
           updateCartTotal(cart);

           return mapToCartItemResponse(savedItem);
        }


        public void removeItemFromCart(Long diningSessionId, long menuItemId){
            Cart cart = getCartByDiningSession(diningSessionId);

            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                    new ResourceNotFoundException("Menu Item Not found with id: " + menuItemId));

            CartItem existingCartItem = cartItemRepository.findByCartAndMenuItem(cart, menuItem)
                    .orElseThrow(()-> new ResourceNotFoundException("Item Not Found in cart"));

            cartItemRepository.delete(existingCartItem);

            cart.getItems().remove(existingCartItem);

            updateCartTotal(cart);

        }

        public ApiResponse updateItemQuantity(Long diningSessionId, Long menuItemId, int quantity) {

            Cart cart = getCartByDiningSession(diningSessionId);

            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Menu Item not found with id: " + menuItemId));

            CartItem cartItem = cartItemRepository.findByCartAndMenuItem(cart, menuItem)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Item not found in cart"));

            // Prevent invalid requests
            if (quantity == 0) {
                throw new BadRequestException("Quantity cannot be zero.");
            }

            int newQuantity = cartItem.getQuantity() + quantity;

            // If quantity becomes zero, remove the item from the cart
            if (newQuantity == 0) {
                cartItemRepository.delete(cartItem);
                cart.getItems().remove(cartItem);
                updateCartTotal(cart);
                return new ApiResponse("Item removed from cart successfully."); // Or return a custom response if you prefer
            }

            // Prevent negative quantity
            if (newQuantity < 0) {
                throw new BadRequestException("Quantity cannot be negative.");
            }

            cartItem.setQuantity(newQuantity);

            CartItem savedItem = cartItemRepository.save(cartItem);

            updateCartTotal(cart);

            return new ApiResponse("Item quantity updated successfully.");
        }


        private CartItemResponseDTO mapToCartItemResponse(CartItem cartItem){

            CartItemResponseDTO dto = new CartItemResponseDTO();

            dto.setMenuItemId(cartItem.getMenuItem().getId());

            dto.setMenuItemName(cartItem.getMenuItem().getName());

            dto.setQuantity(cartItem.getQuantity());

            dto.setUnitPrice(cartItem.getUnitPrice());

            dto.setTotalPrice(cartItem.getUnitPrice()*cartItem.getQuantity());

             return dto;
        }

        private CartResponseDTO mapToCartResponse(Cart cart){

            CartResponseDTO dto = new CartResponseDTO();

            dto.setCartId(cart.getId());

            dto.setDiningSessionId(cart.getDiningSession().getId());

            dto.setItems(cart.getItems().stream()
                    .map(this::mapToCartItemResponse).toList());

            dto.setTotalAmount(cart.getTotalAmount());

            return dto;

        }

    }


