package com.nikkath.service;

import com.nikkath.model.Customer;
import com.nikkath.model.DiningSession;
import com.nikkath.repository.DiningSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiningSessionService {

    @Autowired
    private DiningSessionRepository diningSessionRepository;

    public DiningSession createSession(
            Customer customer,
            Integer tableNo) {

      Optional<DiningSession> existingSession =    diningSessionRepository.
              findByCustomerAndActiveTrue(customer);

      if(existingSession.isPresent()){
          return existingSession.get();
      }

        DiningSession session = new DiningSession();

        session.setCustomer(customer);
        session.setTableNo(tableNo);
        session.setActive(true);

        return diningSessionRepository.save(session);
    }
    public boolean hasActiveSession(Customer customer) {

        return diningSessionRepository
                .findByCustomerAndActiveTrue(customer)
                .isPresent();
    }
}