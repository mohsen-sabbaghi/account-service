package com.example.accountservice.service.interfaces;

import com.example.accountservice.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/7/2022
 */
public interface AccountServiceInterface {

    AccountDto createAccountForExistingCustomer(long customerId, long initCredit) throws ResponseStatusException;

    AccountDto findById(Long id) throws ResponseStatusException;

    Page<AccountDto> findAll(Pageable pageable) throws ResponseStatusException;
}
