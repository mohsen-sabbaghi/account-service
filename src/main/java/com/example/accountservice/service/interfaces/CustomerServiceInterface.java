package com.example.accountservice.service.interfaces;

import com.example.accountservice.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/11/2022
 */
public interface CustomerServiceInterface {

    CustomerDto findById(Long id) throws ResponseStatusException;

    CustomerDto save(CustomerDto customerDto);

    Page<CustomerDto> findAll(Pageable pageable) throws ResponseStatusException;
}
