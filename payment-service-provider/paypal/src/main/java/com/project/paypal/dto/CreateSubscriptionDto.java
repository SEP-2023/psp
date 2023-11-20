package com.project.paypal.dto;

import lombok.Data;

@Data
public class CreateSubscriptionDto {

    private String amount;
    private String frequency;
    private String transactionId;
    private String agencyId;

}
