package com.nikkath.service;
import com.nikkath.model.Customer;
import com.nikkath.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(String name, String phoneNo) {
        Optional<Customer> existingCustomer = customerRepository.findByPhoneNo(phoneNo);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNo(phoneNo);

        return customerRepository.save(customer);


    }
    public Optional<Customer> getCustomerByPhoneNo(String phoneNo) {
        return customerRepository.findByPhoneNo(phoneNo);
    }
}

