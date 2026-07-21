package com.nikkath.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "dining_session_id", nullable = false, unique = true)
    private DiningSession diningSession;

    private double subtotal;

    private double gst;

    private double totalAmount;

    private LocalDateTime generatedAt;
}