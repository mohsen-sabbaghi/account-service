package com.example.accountservice.controller.endtoend;

import com.example.accountservice.AccountServiceApplication;
import com.example.accountservice.controller.CustomerController;
import com.example.accountservice.dto.CustomerDto;
import com.example.accountservice.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/22/2022
 */

@SpringBootTest(classes = AccountServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Customer customerDavid;

    @Test
    void contextLoads() {
        assertNotNull(customerController);
        assertNotNull(restTemplate);
    }

    @BeforeEach
    void setUp() {
        customerDavid = new Customer();
        customerDavid.setName("David");
        customerDavid.setSurname("Copperfield");
    }

    @Test
    @Transactional
    void getCustomersList() {
        String url = "http://localhost:" + port + "/v1/customers/";
        ResponseEntity<List<CustomerDto>> customerDtoList = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>() {
        });
        List<CustomerDto> responseEntity = customerDtoList.getBody();
        assert responseEntity != null;
        assertEquals("Mohsen", responseEntity.get(0).getName());
        assertEquals("Sabbaghi", responseEntity.get(0).getSurname());
    }

    @Test
    void getCustomerById() {
        String url = "http://localhost:" + port + "/v1/customers/" + 1;
        ResponseEntity<CustomerDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, CustomerDto.class);
        CustomerDto customerDto = responseEntity.getBody();
        assert customerDto != null;
        assertEquals("Mohsen", customerDto.getName());
        assertEquals("Sabbaghi", customerDto.getSurname());
    }

    @Test
    void createCustomer() {

        String url = "http://localhost:" + port + "/v1/customers/";
        ResponseEntity<CustomerDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(customerDavid), CustomerDto.class);
        CustomerDto customerDto = responseEntity.getBody();

        assert customerDto != null;
        assertEquals("David", customerDto.getName());
        assertEquals("Copperfield", customerDto.getSurname());
    }

}