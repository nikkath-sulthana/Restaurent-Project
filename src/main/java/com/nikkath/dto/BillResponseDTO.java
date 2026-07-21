package com.nikkath.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillResponseDTO {

    private Long billId;

    private Long diningSessionId;

    private double subtotal;

    private double gst;

    private double totalAmount;

    private LocalDateTime generatedAt;
}