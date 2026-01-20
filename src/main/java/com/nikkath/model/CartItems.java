package com.nikkath.model;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
//So CartItem acts as a bridge between:Cart,MenuItem

@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name ="menu_item_by_id")
    private MenuItem menuItem;

    private int quantity;

    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public CartItems() {

    }

    public CartItems(Cart cart, MenuItem menuItem, int quantity, int price) {
        this.cart = cart;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }
}
