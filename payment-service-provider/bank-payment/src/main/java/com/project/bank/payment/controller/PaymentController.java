package com.project.bank.payment.controller;

import com.project.bank.payment.dto.CompleteTransactionDto;
import com.project.bank.payment.dto.PaymentUrlRequestDto;
import com.project.bank.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/getPaymentUrl")
    public ResponseEntity<?> getPaymentUrl(@RequestBody PaymentUrlRequestDto dto){
        System.out.println("evo ga u bank payment");
        return new ResponseEntity<>(paymentService.getPaymentUrl(dto), HttpStatus.OK);
    }

    @PostMapping("/completeTransaction")
    public ResponseEntity<?> completeTransaction(@RequestBody CompleteTransactionDto dto){
        System.out.println("evo ga u bank payment");
        return new ResponseEntity<>(paymentService.completeTransaction(dto), HttpStatus.OK);
    }
}
