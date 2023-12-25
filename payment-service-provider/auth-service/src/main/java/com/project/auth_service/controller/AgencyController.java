package com.project.auth_service.controller;

import com.project.auth_service.dto.*;
import com.project.auth_service.service.AgencyService;
import com.project.auth_service.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;
    private final PaymentMethodService paymentMethodService;

    @PostMapping("/register")
    public RegisterAgencyResponse registerShop(@RequestBody RegisterAgencyDto dto) {
        return agencyService.registerShop(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto dto){

        LoginResponse response= agencyService.loginAgency(dto);
        if(response != null){
            if (response.getToken() != null){
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
