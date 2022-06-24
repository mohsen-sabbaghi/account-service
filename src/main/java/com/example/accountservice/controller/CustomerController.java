package com.example.accountservice.controller;

import com.example.accountservice.dto.CustomerDto;
import com.example.accountservice.service.interfaces.CustomerServiceInterface;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/11/2022
 */
@Controller
@RequestMapping("/v1")
@Slf4j
public class CustomerController {

    private final CustomerServiceInterface customerServiceInterface;
    @Value("${server.port}")
    private int serverPort;

    public CustomerController(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }

    @GetMapping("/customers")
    @ResponseBody
    public ResponseEntity<List<CustomerDto>> getCustomersList(Pageable pageable) throws ResponseStatusException {
        Page<CustomerDto> page = customerServiceInterface.findAll(pageable);
        try {
            return ResponseEntity.ok().body(page.getContent());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers ");
        }
    }

    @RequestMapping("/customers/{customer-id}")
    @ResponseBody
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customer-id") long customerId) throws ResponseStatusException {
        try {
            return ResponseEntity.ok().body(customerServiceInterface.findById(customerId));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found with ID=" + customerId);
        }
    }

    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) throws ResponseStatusException, URISyntaxException {
        if (customerDto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Request Body!");
        String jsonBody = new Gson().toJson(customerDto, CustomerDto.class);
        log.debug("New customer to save : {}", jsonBody);
        if (customerDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID will generate by System");
        }
        CustomerDto result = customerServiceInterface.save(customerDto);
        return ResponseEntity.created(new URI("http://localhost:" + serverPort + "/v1/customers/" + result.getId())).body(result);
    }

    @RequestMapping("/ui/customers")
    public String getCustomerListForWeb(Model model, Pageable pageable) {
        log.debug("#Web request for get customer");
        Page<CustomerDto> customerDtoList = customerServiceInterface.findAll(pageable);
        model.addAttribute("customerList", customerDtoList.toList());
        return "customer/customer-list";
    }

}
