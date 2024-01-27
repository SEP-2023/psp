package com.project.auth_service.dto;

import lombok.Data;

@Data
public class RegisterAgencyResponse {
    private String agencyId;
    private String secret;
    private String qrCode;
}
