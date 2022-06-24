package com.example.accountservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {
    Customer customer;
    Customer tempCustomer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        tempCustomer = new Customer();
    }

    @Test
    void testEquals() {
        customer.setId(1L);
        tempCustomer.setId(1L);
        boolean isEqual = customer.equals(tempCustomer);
        assertTrue(isEqual);
    }

    @Test
    void getCreatedTime() {
        customer.setCreatedTime(new Date());
        assertEquals(new Date(), customer.getCreatedTime());
    }

    @Test
    void getName() {
        tempCustomer.setName("Mohsen");
        assertEquals("Mohsen", tempCustomer.getName());
    }

    @Test
    void getSurname() {
        customer.setSurname("Sabbaghi");
        assertEquals("Sabbaghi", customer.getSurname());
    }
}