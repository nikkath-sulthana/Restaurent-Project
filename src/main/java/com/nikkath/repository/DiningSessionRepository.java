package com.nikkath.repository;
import com.nikkath.model.DiningSession;
import com.nikkath.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DiningSessionRepository extends JpaRepository<DiningSession, Long> {

    Optional<DiningSession> findByCustomerAndActiveTrue (Customer customer);
}
