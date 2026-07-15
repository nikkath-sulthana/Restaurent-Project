package com.nikkath.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalAmount;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
     private List<CartItem> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "dining_session_id")
    private DiningSession diningSession;

}
