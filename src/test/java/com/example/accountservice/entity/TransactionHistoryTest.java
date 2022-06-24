package com.example.accountservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionHistoryTest {
    TransactionHistory transactionHistory;

    @BeforeEach
    void setUp() {
        transactionHistory = new TransactionHistory();
    }

    @Test
    void getAccount() {
        Account acc = new Account();
        transactionHistory.setAccount(acc);
        assertEquals(acc, transactionHistory.getAccount());
    }

    @Test
    void getAmount() {
        transactionHistory.setAmount(200L);
        assertEquals(200L, transactionHistory.getAmount());
    }


    @Test
    void getTrackNo() {
        transactionHistory.setTrackNo(123456789L);
        assertEquals(123456789L, transactionHistory.getTrackNo());
    }
}