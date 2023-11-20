package com.project.paypal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class PaypalTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transactionId", unique = true)
    private String transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "agencyId")
    private String agencyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus transactionStatus;

}


