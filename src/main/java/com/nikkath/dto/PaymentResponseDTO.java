package com.nikkath.dto;


import com.nikkath.model.PaymentMethod;
import com.nikkath.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {

    private Long id;

    private Long billId;

    private double amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paidAt;

    private String receiptNumber;

    private String transactionId;
}
