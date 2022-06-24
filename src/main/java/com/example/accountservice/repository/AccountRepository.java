package com.example.accountservice.repository;

import com.example.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/8/2022
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
