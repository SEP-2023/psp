package com.project.bank.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentUrlRequestDto {
    private String merchantId;
    private Double amount;
    private String merchantOrderId;
    private LocalDateTime merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
}
