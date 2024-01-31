package com.project.auth_service.controller;

import com.project.auth_service.dto.*;
import com.project.auth_service.service.AgencyService;
import com.project.auth_service.service.LoggerService;
import com.project.auth_service.service.PaymentMethodService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;
    private final PaymentMethodService paymentMethodService;

    private final LoggerService logger = new LoggerService(this.getClass());

    @PostMapping("/register")
    public RegisterAgencyResponse registerShop(@RequestBody RegisterAgencyDto dto) throws QrGenerationException {
        logger.info(MessageFormat.format("Registering a new agency with name {0}", dto.getName()));
        return agencyService.registerShop(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto dto){

        LoginResponse response= agencyService.loginAgency(dto);
        if(response != null){
            if (response.getToken() != null){
                logger.success(MessageFormat.format("Agency with mail {0} successfully logged in", dto.getMail()));
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }
        logger.error(MessageFormat.format("Agency with mail {0} failed to log in", dto.getMail()));
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
