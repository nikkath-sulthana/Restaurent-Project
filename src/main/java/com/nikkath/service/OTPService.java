package com.nikkath.service;
import com.nikkath.model.Customer;
import com.nikkath.model.OTPDetails;
import com.nikkath.repository.CustomerRepository;
import com.nikkath.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Random;
@Service
public class OTPService {
    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DiningSessionService diningSessionService;

    public String generateOtp(String phoneNo){
    Optional<Customer> customer =customerRepository.findByPhoneNo(phoneNo);

    if(customer.isPresent() && diningSessionService.hasActiveSession(customer.get())){
        throw new RuntimeException("Customer already has an active dining Session");
    }


        String otp = String.valueOf(100000+ new Random().nextInt(900000));
        OTPDetails otpDetails = new OTPDetails();
        otpDetails.setPhoneNo(phoneNo);
        otpDetails.setOtp(otp);
        otpDetails.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpDetails.setVerified(false);

        otpRepository.save(otpDetails);

        return otp;
    }

    public boolean verifyOtp(String phoneNo, String enteredOtp){
        Optional<OTPDetails> otpRecord = otpRepository.findTopByPhoneNoOrderByIdDesc(phoneNo);

        if(otpRecord.isEmpty()){
            return false;
        }
        OTPDetails otp = otpRecord.get();

        if(!otp.getOtp().equals(enteredOtp)){
            return false;
        }

        if(otp.getExpiryTime().isBefore(LocalDateTime.now())){
            return false;
        }
        otp.setVerified(true);
        otpRepository.save(otp);
        return true;
    }

}
