package com.project.bank.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant")
    private Merchant merchant;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "merchantOrderId")
    private String merchantOrderId;

    @Column(name = "merchantTimestamp")
    private LocalDateTime merchantTimestamp;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "successUrl")
    private String successUrl;

    @Column(name = "failedUrl")
    private String failedUrl;

    @Column(name = "errorUrl")
    private String errorUrl;

    @Column(name = "paymentId")
    private String paymentId;

    @Column(name = "acquirerOrderId")
    private String acquirerOrderId;

    @Column(name = "acquirerTimestamp")
    private LocalDateTime acquirerTimestamp;

}
