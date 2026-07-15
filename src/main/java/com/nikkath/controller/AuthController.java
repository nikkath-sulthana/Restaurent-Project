package com.nikkath.controller;
import com.nikkath.dto.ApiResponse;
import com.nikkath.exception.InvalidOtpException;
import com.nikkath.model.Customer;
import com.nikkath.dto.SendOtpRequest;
import com.nikkath.dto.VerifyOtpRequest;
import com.nikkath.service.CustomerService;
import com.nikkath.service.OTPService;
import com.nikkath.service.DiningSessionService;

import com.nikkath.dto.LoginResponse;
import com.nikkath.model.DiningSession;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private DiningSessionService diningSessionService;

    @PostMapping("/send-otp")
    public ResponseEntity <ApiResponse> sendOtp(@Valid @RequestBody SendOtpRequest request){
      String otp =  otpService.generateOtp(request.getPhoneNo());

        return ResponseEntity.ok(new ApiResponse("OTP generated Successfully : " + otp));

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<LoginResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        boolean isVerified = otpService.verifyOtp(
                request.getPhoneNo(),
                request.getOtp()
        );
        if (!isVerified) {
            throw new InvalidOtpException("Invalid or Expired OTP");
        }

        Customer customer = customerService.registerCustomer(request.getName(), request.getPhoneNo());

        DiningSession session = diningSessionService.createSession(customer, request.getTableNo());

        return ResponseEntity.ok(new LoginResponse(200, "Login Successful",
                customer.getId(), session.getId()));
    }

}
