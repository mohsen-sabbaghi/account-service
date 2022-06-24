package com.example.accountservice.bootstrap;

import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Customer;
import com.example.accountservice.entity.TransactionHistory;
import com.example.accountservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/8/2022
 */
@Configuration
@Slf4j
@Profile("!pro")
public class MockedData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public MockedData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {

//        =============================Customer 1==============================

        Customer customerMohsen = new Customer();
        customerMohsen.setName("Mohsen");
        customerMohsen.setSurname("Sabbaghi");
        //---------------customer 1 account-------------------
        Account mohsenAccount = new Account();
        mohsenAccount.setCustomer(customerMohsen);//mohsen
        //--------------account 1 transactions----------------
        TransactionHistory transaction1 = new TransactionHistory(1000);
        mohsenAccount.addTransaction(transaction1);
        TransactionHistory transaction2 = new TransactionHistory(-500);
        mohsenAccount.addTransaction(transaction2);
        //--------------and acc to customer then save----------------
        customerMohsen.addAccount(mohsenAccount);
        customerRepository.save(customerMohsen);
        log.debug("#customer1 : {}", customerMohsen);

//        =============================Customer 2==============================
        Customer customerJohnny = new Customer();
        customerJohnny.setName("Johnny");
        customerJohnny.setSurname("Depp");
        //---------------create account for customer 1-------------------
        Account johnnyAccount = new Account();
        Account johnnyAccount2 = new Account();
        johnnyAccount.setCustomer(customerJohnny);
        johnnyAccount2.setCustomer(customerJohnny);
        //--------------account 1 transactions----------------
        TransactionHistory t1 = new TransactionHistory(3000);
        johnnyAccount.addTransaction(t1);

        TransactionHistory t2 = new TransactionHistory(-1600);
        johnnyAccount.addTransaction(t2);

        TransactionHistory t3 = new TransactionHistory(-1000);
        johnnyAccount2.addTransaction(t3);
        //--------------and acc to customer then save----------------
        customerJohnny.addAccount(johnnyAccount);
        customerJohnny.addAccount(johnnyAccount2);
        customerRepository.save(customerJohnny);
        log.debug("#customer2 : {}", customerJohnny);

    }


}
