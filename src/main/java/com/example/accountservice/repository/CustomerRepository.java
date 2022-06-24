package com.example.accountservice.repository;

import com.example.accountservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/8/2022
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
