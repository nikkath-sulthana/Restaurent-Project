package com.nikkath.dto;

import jakarta.validation.constraints.*;


import lombok.Data;

@Data
public class SendOtpRequest {
    @NotBlank(message = "Name is required")
    private String name;


    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Phone Number is Required")
    private String phoneNo;

    @NotNull(message = "Table Number is required")
    @Min(value= 1, message= "Table number must be between 1 and 10")
    @Max(value = 10, message = "Table number must be between 1 and 10" )
    private Integer tableNo;



}
