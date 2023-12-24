package com.project.paypal.dto;

import lombok.Data;

@Data
public class ConfirmPaymentDto {

    private String agencyId;
    private Double amount;
    private String transactionId;
    private String paymentId;
    private String token;
    private String payerId;

}
