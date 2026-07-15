package com.nikkath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private int status;
    private String message;
    private Long customerId;
    private Long sessionId;
}