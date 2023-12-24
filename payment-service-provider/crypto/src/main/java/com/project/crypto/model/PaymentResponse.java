package com.project.crypto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {

    private String id;
    private String status;
    private String payment_url;
}
