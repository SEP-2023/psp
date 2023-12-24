package com.project.auth_service.dto;

import lombok.Data;

@Data
public class AddPaymentMethodDto {

    private String paymentMethod;
    private String agencyId;
}
