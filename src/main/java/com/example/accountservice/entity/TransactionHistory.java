package com.example.accountservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/8/2022
 */


@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_transaction")
public class TransactionHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @NotNull
    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "track_no")
    private Long trackNo;

    @ManyToOne
    @JoinColumn(name = "fk_account")
    private Account account;

    public TransactionHistory(long amount) {
        this.amount = amount;
    }

    @PrePersist
    public void prePersist() {
        setCreatedTime(new Date());
    }
}
