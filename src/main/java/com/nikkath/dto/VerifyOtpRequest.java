package com.nikkath.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Phone Number is Required")
    private String phoneNo;

    @NotBlank(message = "OTP is Required")
    private String otp;

    @NotNull(message = "Table Number is required")
    @Min(value= 1, message= "Table number must be between 1 and 10")
    @Max(value = 10, message = "Table number must be between 1 and 10" )
    private Integer tableNo;

}