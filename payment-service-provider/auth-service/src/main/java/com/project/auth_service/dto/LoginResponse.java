package com.project.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String agencyId;
    private String token;
    private String roles;

}
