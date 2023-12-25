package com.project.auth_service.dto;

import lombok.Data;

@Data
public class AgencyInformationDto {

    private String agencyId;
    private String transactionId;
    private String amount;
    private String accessToken;
}
