package com.nikkath.repository;
import com.nikkath.model.DiningSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiningRepository extends JpaRepository<DiningSession, Long> {
}
