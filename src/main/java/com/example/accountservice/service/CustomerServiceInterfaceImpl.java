package com.example.accountservice.service;

import com.example.accountservice.dto.CustomerDto;
import com.example.accountservice.entity.Customer;
import com.example.accountservice.repository.CustomerRepository;
import com.example.accountservice.service.interfaces.CustomerServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/11/2022
 */
@Service
@Transactional
@Slf4j
public class CustomerServiceInterfaceImpl implements CustomerServiceInterface {
    private final CustomerRepository customerRepository;

    public CustomerServiceInterfaceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto findById(Long id) throws ResponseStatusException {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(c -> new ModelMapper().map(c, CustomerDto.class))
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
                );
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new ModelMapper().map(customerDto, Customer.class);
        customer = customerRepository.save(customer);
        return new ModelMapper().map(customer, CustomerDto.class);
    }

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {
        try {
            return customerRepository.findAll(pageable).map(customer -> new ModelMapper().map(customer, CustomerDto.class));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found", e);
        }
    }
}
