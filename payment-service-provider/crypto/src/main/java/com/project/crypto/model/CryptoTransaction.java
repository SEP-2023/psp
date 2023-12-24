package com.project.crypto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class CryptoTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transactionId", unique = true)
    private String transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "priceCurrency")
    private String priceCurrency;

    @Column(name = "agencyId")
    private String agencyId;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

}
