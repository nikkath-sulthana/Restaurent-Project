package com.nikkath.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nikkath.model.OTPDetails;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTPDetails, Long> {
    Optional<OTPDetails> findByPhoneNo(String phoneNo);
}
