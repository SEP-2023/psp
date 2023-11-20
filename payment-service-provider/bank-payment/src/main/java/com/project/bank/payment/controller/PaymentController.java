package com.project.bank.payment.controller;

import com.project.bank.payment.dto.PaymentUrlRequestDto;
import com.project.bank.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/getPaymentUrl")
    public ResponseEntity<?> getPaymentUrl(PaymentUrlRequestDto dto){
        return new ResponseEntity<>(paymentService.getPaymentUrl(dto), HttpStatus.OK);
    }
}
