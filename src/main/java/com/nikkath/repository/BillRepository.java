package com.nikkath.repository;

import com.nikkath.model.Bill;
import com.nikkath.model.DiningSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByDiningSession(DiningSession diningSession);

}
