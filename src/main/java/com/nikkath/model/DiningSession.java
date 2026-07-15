package com.nikkath.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;




@Data
@Table(name = "dining_sessions")
@Entity
public class DiningSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer tableNo;

    private LocalDateTime startAt = LocalDateTime.now();

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "diningSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Cart cart;

}
