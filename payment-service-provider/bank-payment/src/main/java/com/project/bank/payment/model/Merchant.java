package com.project.bank.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "merchantId")
    private String merchantId;

    @Column(name = "merchantPassword")
    private String merchantPassword;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank")
    private Bank bank;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
