package com.example.accountservice.controller.integration;

import com.example.accountservice.AccountServiceApplication;
import com.example.accountservice.dto.CustomerDto;
import com.example.accountservice.entity.Customer;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/22/2022
 */

@SpringBootTest(classes = AccountServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private AccountRepository accountsRepo;

    private MockMvc mockedCustomer;

    private Customer customer;

    @BeforeEach
    public void initTest() {
        customer = new Customer();
        customer.setName("Mohsen");
        customer.setSurname("Sabbaghi");

        mockedCustomer = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    void createCustomer() throws Exception {
        int sizeBeforeCreate = customerRepo.findAll().size();

        CustomerDto customerDto = new ModelMapper().map(customer, CustomerDto.class);

        mockedCustomer
                .perform(post("/v1/customers").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(customerDto)))
                .andExpect(status().isCreated());

        List<Customer> customerList = customerRepo.findAll();
        assertThat(customerList).hasSize(sizeBeforeCreate + 1);

        Customer testCustomer = customerList.get(customerList.size() - 1);

        assertThat(testCustomer.getName()).isEqualTo("Mohsen");
        assertThat(testCustomer.getSurname()).isEqualTo("Sabbaghi");
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {

        int count = Math.toIntExact(customerRepo.count());
        customerRepo.save(customer);

        // Get customerList
        mockedCustomer
                .perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Mohsen")))
                .andExpect(jsonPath("$.[*].surname").value(hasItem("Sabbaghi")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(count + 1)));
    }

    @Test
    @Transactional
    void getCustomer() throws Exception {
        customerRepo.saveAndFlush(customer);
        mockedCustomer
                .perform(get("/v1/customers/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$..id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$..name").value(hasItem("Mohsen")))
                .andExpect(jsonPath("$..surname").value(hasItem("Sabbaghi")));
    }

    @Test
    @Transactional
    void getNoExistingCustomer() throws Exception {
        mockedCustomer.perform(get("/v1/customers/" + 5000L))// 5000!?
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }


}