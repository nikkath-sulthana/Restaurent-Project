package com.nikkath.repository;

import com.nikkath.model.Bill;
import com.nikkath.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBill(Bill bill);
}
