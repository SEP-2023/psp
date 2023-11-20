package com.project.paypal.dto;

import lombok.Data;

@Data
public class RedirectToPaypalDto {
    private String redirectUrl;
    private String token;
}
