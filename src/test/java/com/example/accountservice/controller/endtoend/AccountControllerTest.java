package com.example.accountservice.controller.endtoend;

import com.example.accountservice.controller.AccountController;
import com.example.accountservice.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    AccountController accountController;
    @LocalServerPort
    int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void contextLoads() {
        assertNotNull(accountController);
        assertNotNull(restTemplate);
    }

    @Test
    void createAccount() {
        String existingCustomerId = "1";
        String resourceUrl = "http://localhost:" + port + "/v1/customers/" + existingCustomerId + "/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Initial-Credit", "1000");
        headers.add("Track-Id", String.valueOf(System.currentTimeMillis()));

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, AccountDto.class);
        AccountDto accountDto = responseEntity.getBody();
        assert accountDto != null;
        assertEquals(1000, accountDto.getBalance());
//        assertEquals(1, accountDto.getAccountTransactions().size());
    }

    @Test
    void getAccount() {
        String url = "http://localhost:" + port + "/v1/accounts/" + 1; //this ID
        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, AccountDto.class);
        AccountDto accountDto = responseEntity.getBody();
        assert accountDto != null;
        assertEquals(1, accountDto.getId()); // here we expect to get that ID
    }

    @Test
    void getAccountList() {
        String url = "http://localhost:" + port + "/v1/accounts/";
        ResponseEntity<List<AccountDto>> accountDtoList = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDto>>() {
        });
        List<AccountDto> responseEntity = accountDtoList.getBody();
        assert responseEntity != null;
        assertEquals(1, responseEntity.get(0).getId());
        assertEquals(500, responseEntity.get(0).getBalance());
    }

}