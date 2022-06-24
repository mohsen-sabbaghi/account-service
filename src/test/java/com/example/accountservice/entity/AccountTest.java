package com.example.accountservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void getBalance() {
        account.setBalance(100L);
        assertEquals(100L, account.getBalance());
    }

    @Test
    void getTransactionHistories() {
        TransactionHistory history1 = new TransactionHistory(10L);
        TransactionHistory history2 = new TransactionHistory(20L);
        TransactionHistory history3 = new TransactionHistory(30L);

        Set<TransactionHistory> transactionHistorySet = new HashSet<>();
        transactionHistorySet.add(history1);
        transactionHistorySet.add(history2);
        transactionHistorySet.add(history3);

        account.setTransactionHistories(transactionHistorySet);

        assertEquals(3, account.getTransactionHistories().size());
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer();
        customer.setName("mohsen");
        account.setCustomer(customer);
        assertEquals("mohsen", account.getCustomer().getName());
    }
}