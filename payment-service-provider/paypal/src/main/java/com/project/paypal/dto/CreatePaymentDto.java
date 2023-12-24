package com.project.paypal.dto;

import lombok.Data;

@Data
public class CreatePaymentDto {

    private String agencyId;
    private Double amount;
    private String transactionId;

}
