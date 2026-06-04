package com.nikkath.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "otp_details")
public class OTPDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNo;
    private String otp;
    private LocalDateTime expiryTime;
    private boolean verified = false;
}
