package com.project.bank.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompleteTransactionDto {

    private String transactionStatus;
    private String acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private String paymentId;
    private String merchantOrderId;
}
