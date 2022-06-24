package com.example.accountservice.controller.integration;

import com.example.accountservice.AccountServiceApplication;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Customer;
import com.example.accountservice.interceptor.DuplicationInterceptor;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private AccountRepository accountsRepo;
    private MockMvc mockedAccount;
    private Account account;

    @BeforeEach
    public void initTest() {
        account = new Account();
        account.setBalance(1000L);

        mockedAccount = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        int accountsCount = Math.toIntExact(accountsRepo.count());
        accountsRepo.save(account);
        mockedAccount.perform(get("/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(accountsCount + 1)));
    }

    @Test
    @Transactional
    void getSpecificAccount() throws Exception {

        // save a new account
        accountsRepo.save(account);

        // Get the recently saved account
        mockedAccount
                .perform(get("/v1/accounts/" + account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(account.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value((1000)));
    }

    @Test
    @Transactional
    void getNoExistingAccount() throws Exception {
        mockedAccount.perform(get("/v1/accounts/" + 5000)) //there is no account wi ID 5000 at first
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

    }

    @Test
    @Transactional
    void createAccountWithMoreThanZeroCredit() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mohsen");
        customer.setSurname("Sabbaghi");

        customerRepo.saveAndFlush(customer);
        int sizeAccountBeforeServiceCall = accountsRepo.findAll().size();

        mockedAccount.perform(
                post("/v1/customers/" + customer.getId() + "/accounts")
                        .header("Initial-Credit", 1000)
                        .header("Track-Id", 123)
        ).andExpect(status().isCreated()); //Should be 201

        int sizeAccountAfterServiceCall = accountsRepo.findAll().size();
        assertEquals((sizeAccountBeforeServiceCall + 1), sizeAccountAfterServiceCall);

    }

    @Test
    @Transactional
    void createAccountDuplicatedTrackId() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mohsen");
        customer.setSurname("Sabbaghi");

        DuplicationInterceptor.clearCache();

        // first time with 123 Track ID
        createAccountWithMoreThanZeroCredit();

        mockedAccount.perform(
                post("/v1/customers/" + customer.getId() + "/accounts")
                        .header("Initial-Credit", 1000)
                        .header("Track-Id", 123) // again we use 123 as a Track ID
        ).andExpect(status().is4xxClientError()); //Should be 400

        DuplicationInterceptor.clearCache();
    }

    @Test
    @Transactional
    void createAccountForNoExistingCustomer() throws Exception {
        mockedAccount
                .perform(
                        post("/v1/customers/" + 5000 + "/accounts") //customer with ID 5000 doesn't exist
                                .header("Initial-Credit", 1000)
                                .header("Track-Id", System.currentTimeMillis() / 10000))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    @Transactional
    void createAccountWithInitialCreditsLessThanZero() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mohsen");
        customer.setSurname("Sabbaghi");

        customerRepo.save(customer);

        mockedAccount
                .perform(
                        post("/v1/customers/" + customer.getId() + "/accounts")
                                .header("Initial-Credit", (long) -100) //Negative numbers are not allowed
                                .header("Track-Id", System.currentTimeMillis() / 1000))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

}